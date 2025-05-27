package com.mycompany.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.pojo.QuestionOption;
import com.mycompany.pojo.ResponseDetail;
import com.mycompany.pojo.Survey;
import com.mycompany.pojo.SurveyQuestion;
import com.mycompany.pojo.SurveyResponse;
import com.mycompany.pojo.User;
import com.mycompany.repositories.SurveyRepository;
import com.mycompany.repositories.UserRepository;
import com.mycompany.services.SurveyService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SurveyServiceImpl implements SurveyService {
    @Autowired
    private SurveyRepository surveyRepo;
    @Autowired
    private UserRepository userRepo;

    @Override    public List<Survey> getAllSurveys() {
        return surveyRepo.getAllSurveys();
    }

    @Override    public Survey addSurvey(Map<String, Object> params) {
        Survey survey = new Survey();
        survey.setTitle((String) params.get("title"));
        survey.setDescription((String) params.get("description"));
        survey.setStatus((String) params.getOrDefault("status", "DRAFT"));
        survey.setCreatedAt(new Date());
        if (params.get("startDate") != null)
            survey.setStartDate((Date) params.get("startDate"));
        if (params.get("endDate") != null)
            survey.setEndDate((Date) params.get("endDate"));
        if (params.get("createdBy") != null) {
            User user =
            userRepo.getUserById(Long.valueOf(params.get("createdBy").toString()));
            survey.setCreatedByAdminId(user);
        }

        // Map questions
        List<Map<String, Object>> questions = (List<Map<String, Object>>) params.get("questions");
        if (questions != null && !questions.isEmpty()) {
            List<SurveyQuestion> questionEntities = new ArrayList<>();
            for (Map<String, Object> q : questions) {
                SurveyQuestion question = new SurveyQuestion();
                question.setQuestionText((String) q.get("questionText"));
                String questionType = (String) q.get("questionType");
                question.setQuestionType(questionType);
                question.setRequired(q.get("required") != null ? Boolean.valueOf(q.get("required").toString()) : false);
                if (q.get("orderNumber") != null)
                    question.setOrderNumber(Integer.valueOf(q.get("orderNumber").toString()));
                question.setSurveyId(survey);

                // Only map options for SINGLE_CHOICE and MULTIPLE_CHOICE
                if ("SINGLE_CHOICE".equalsIgnoreCase(questionType) || "MULTIPLE_CHOICE".equalsIgnoreCase(questionType)) {
                    List<Map<String, Object>> options = (List<Map<String, Object>>) q.get("options");
                    if (options != null && !options.isEmpty()) {
                        List<QuestionOption> optionEntities = new ArrayList<>();
                        for (Map<String, Object> o : options) {
                            QuestionOption option = new QuestionOption();
                            option.setOptionText((String) o.get("optionText"));
                            if (o.get("orderNumber") != null)
                                option.setOrderNumber(Integer.valueOf(o.get("orderNumber").toString()));
                            option.setQuestionId(question);
                            optionEntities.add(option);
                        }
                        question.setQuestionOptionCollection(optionEntities);
                    }
                } else {
                    // TEXT, RATING: ignore options
                    question.setQuestionOptionCollection(null);
                }
                questionEntities.add(question);
            }
            survey.setSurveyQuestionCollection(questionEntities);
        }
        return surveyRepo.addSurvey(survey);
    }    @Override    public Survey updateSurveyStatus(Long surveyId, Map<String, String> params) {
        Survey survey = surveyRepo.getSurveyById(surveyId);
        if (survey != null) {
            survey.setStatus(params.get("status"));
            survey.setUpdatedAt(new Date());
            return surveyRepo.updateSurveyStatus(surveyId, params);
        }
        return null;
    }

    @Override    public Map<String, Object> getSurveyDetail(Long surveyId) {
        return surveyRepo.getSurveyDetail(surveyId);
    }

    @Override    public Map<String, Object> getSurveyResults(Long surveyId) {
        return surveyRepo.getSurveyResults(surveyId);
    }

    @Override    public List<Survey> getAvailableSurveysForUser() {
        return surveyRepo.getAvailableSurveysForUser();
    }

    @Override    public Map<String, Object> getSurveyDetailForUser(Long surveyId) {
        return surveyRepo.getSurveyDetailForUser(surveyId);
    }

    @Override
    public boolean respondSurvey(Long surveyId, Map<String, Object> params) {
        // Lấy user trả lời
        Long userId = params.get("userId") != null ? Long.valueOf(params.get("userId").toString()) : null;
        if (userId == null) return false;
        User user = userRepo.getUserById(userId);
        if (user == null) return false;
        Survey survey = surveyRepo.getSurveyDetail(surveyId) != null ? (Survey) surveyRepo.getSurveyDetail(surveyId).get("survey") : null;
        if (survey == null) return false;

        SurveyResponse response = new SurveyResponse();
        response.setSurveyId(survey);
        response.setResidentId(user);
        response.setSubmittedAt(new java.util.Date());

        // Mapping các câu trả lời chi tiết
        List<Map<String, Object>> answers = (List<Map<String, Object>>) params.get("answers");
        if (answers != null && !answers.isEmpty()) {
            List<ResponseDetail> detailEntities = new ArrayList<>();
            for (Map<String, Object> a : answers) {
                ResponseDetail detail = new ResponseDetail();
                // questionId là bắt buộc
                Long questionId = a.get("questionId") != null ? Long.valueOf(a.get("questionId").toString()) : null;
                if (questionId == null) continue;
                detail.setQuestionId(new SurveyQuestion(questionId));
                detail.setResponseId(response);
                // Nếu là TEXT thì lưu textAnswer
                if (a.get("textAnswer") != null)
                    detail.setTextAnswer(a.get("textAnswer").toString());
                // Nếu là RATING thì lưu ratingValue
                if (a.get("ratingValue") != null)
                    detail.setRatingValue(Integer.valueOf(a.get("ratingValue").toString()));
                // Nếu là SINGLE_CHOICE thì lưu optionId
                if (a.get("optionId") != null)
                    detail.setOptionId(new QuestionOption(Long.valueOf(a.get("optionId").toString())));
                // Nếu là MULTIPLE_CHOICE thì lưu danh sách optionIds
                if (a.get("optionIds") != null && a.get("optionIds") instanceof List) {
                    List<Long> optionIds = (List<Long>) a.get("optionIds");
                    // Lưu từng optionId thành 1 ResponseDetail riêng biệt
                    for (Long oid : optionIds) {
                        ResponseDetail multiDetail = new ResponseDetail();
                        multiDetail.setQuestionId(new SurveyQuestion(questionId));
                        multiDetail.setResponseId(response);
                        multiDetail.setOptionId(new QuestionOption(oid));
                        detailEntities.add(multiDetail);
                    }
                    continue; // đã add hết rồi
                }
                detailEntities.add(detail);
            }
            response.setResponseDetailCollection(detailEntities);
        }
        // Repository chỉ nhận entity SurveyResponse, hoặc truyền surveyId, userId, response entity nếu cần
        return surveyRepo.respondSurvey(surveyId, userId, response);
    }
}
