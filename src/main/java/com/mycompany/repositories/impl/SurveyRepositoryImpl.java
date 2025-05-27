package com.mycompany.repositories.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.mycompany.pojo.QuestionOption;
import com.mycompany.pojo.Survey;
import com.mycompany.pojo.SurveyQuestion;
import com.mycompany.pojo.SurveyResponse;
import com.mycompany.pojo.User;
import com.mycompany.repositories.SurveyRepository;

@Repository
public class SurveyRepositoryImpl implements SurveyRepository {

    @Autowired
    private LocalSessionFactoryBean factory;    
    @Override
    public List<Survey> getAllSurveys() {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            List<Survey> surveys = session.createNamedQuery("Survey.findAll", Survey.class).getResultList();
            return surveys != null ? surveys : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return new ArrayList<>(); // Return empty list instead of null
        }
    }    @Override
    public Survey addSurvey(Survey survey) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            session.save(survey);
            return survey;
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Failed to add survey", e);
        }
    }    @Override
    public Survey updateSurveyStatus(Long surveyId, Map<String, String> params) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            Survey s = session.get(Survey.class, surveyId);
            if (s != null) {
                if (params.containsKey("status")) {
                    s.setStatus(params.get("status"));
                }
                session.update(s);
            }
            return s;
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Failed to update survey status", e);
        }
    }    @Override
    public Map<String, Object> getSurveyDetail(Long surveyId) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            Survey s = session.get(Survey.class, surveyId);
            if (s == null) {
                throw new RuntimeException("Survey not found with id: " + surveyId);
            }
            // Lấy câu hỏi và lựa chọn
            List<SurveyQuestion> questions = session.createQuery(
                    "FROM SurveyQuestion WHERE surveyId.surveyId = :sid ORDER BY orderNumber ASC", SurveyQuestion.class)
                    .setParameter("sid", surveyId).getResultList();
            for (SurveyQuestion q : questions) {
                List<QuestionOption> options = session.createQuery(
                        "FROM QuestionOption WHERE questionId.questionId = :qid ORDER BY orderNumber ASC", QuestionOption.class)
                        .setParameter("qid", q.getQuestionId()).getResultList();
                q.setQuestionOptionCollection(options);
            }
            return Map.of("survey", s, "questions", questions);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Failed to get survey details", e);
        }
    }

    @Override
    public Map<String, Object> getSurveyResults(Long surveyId) {
        try {
            Map<String, Object> detail = getSurveyDetail(surveyId);
            if (detail == null) return null;
            
            List<SurveyQuestion> questions = (List<SurveyQuestion>) detail.get("questions");
            Session session = this.factory.getObject().getCurrentSession();
            List<Map<String, Object>> results = new ArrayList<>();
            
            for (SurveyQuestion q : questions) {
                String type = q.getQuestionType();
                Map<String, Object> qResult = new HashMap<>();
                qResult.put("questionId", q.getQuestionId());
                qResult.put("questionText", q.getQuestionText());
                qResult.put("questionType", type);
                
                if ("SINGLE_CHOICE".equalsIgnoreCase(type) || "MULTIPLE_CHOICE".equalsIgnoreCase(type)) {
                    List<QuestionOption> options = session.createQuery(
                        "FROM QuestionOption WHERE questionId.questionId = :qid ORDER BY orderNumber ASC", QuestionOption.class)
                        .setParameter("qid", q.getQuestionId()).getResultList();
                    List<Map<String, Object>> optionStats = new ArrayList<>();
                    for (QuestionOption o : options) {
                        Long count = session.createQuery(
                            "SELECT COUNT(rd) FROM ResponseDetail rd WHERE rd.questionId.questionId = :qid AND rd.optionId.optionId = :oid", Long.class)
                            .setParameter("qid", q.getQuestionId())
                            .setParameter("oid", o.getOptionId())
                            .uniqueResult();
                        Map<String, Object> opt = new HashMap<>();
                        opt.put("optionId", o.getOptionId());
                        opt.put("optionText", o.getOptionText());
                        opt.put("count", count != null ? count : 0);
                        optionStats.add(opt);
                    }
                    qResult.put("options", optionStats);
                } else {
                    List<String> textResponses = session.createQuery(
                        "SELECT rd.textResponse FROM ResponseDetail rd WHERE rd.questionId.questionId = :qid AND rd.textResponse IS NOT NULL", String.class)
                        .setParameter("qid", q.getQuestionId())
                        .getResultList();
                    qResult.put("textResponses", textResponses);
                }
                results.add(qResult);
            }
            return Map.of("survey", detail.get("survey"), "results", results);
        } catch (Exception e) {
            return null;
        }
    }    @Override
    public List<Survey> getAvailableSurveysForUser() {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            List<Survey> surveys = session.createQuery("FROM Survey WHERE status = 'ACTIVE'", Survey.class).getResultList(); 
            return surveys != null ? surveys : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Failed to get available surveys", e);
        }
    }

    @Override
    public Map<String, Object> getSurveyDetailForUser(Long surveyId) {
        try {
            return getSurveyDetail(surveyId);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Failed to get survey details for user", e);
        }
    }    @Override
    public boolean respondSurvey(Long surveyId, Long userId, SurveyResponse response) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            User u = session.get(User.class, userId);
            if (u == null) {
                throw new RuntimeException("User not found with id: " + userId);
            }
            // Kiểm tra đã tồn tại phản hồi chưa (1 user chỉ được trả lời 1 lần)
            Long count = session.createQuery(
                "SELECT COUNT(r) FROM SurveyResponse r WHERE r.surveyId.surveyId = :sid AND r.residentId.id = :uid", Long.class)
                .setParameter("sid", surveyId)
                .setParameter("uid", u.getId())
                .uniqueResult();
            if (count != null && count > 0) {
                return false;
            }
            session.save(response);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Failed to submit survey response", e);
        }
    }
}
