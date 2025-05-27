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
        Session session = this.factory.getObject().getCurrentSession();
        return session.createNamedQuery("Survey.findAll", Survey.class).getResultList();
    }

    @Override
    public Survey addSurvey(Survey survey) {
        Session session = this.factory.getObject().getCurrentSession();
        session.save(survey);
        return survey;
    }

    @Override
    public Survey updateSurveyStatus(Long surveyId, Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        Survey s = session.get(Survey.class, surveyId);
        if (s != null) {
            if (params.containsKey("status")) {
                s.setStatus(params.get("status"));
            }
            session.update(s);
        }
        return s;
    }

    @Override
    public Map<String, Object> getSurveyDetail(Long surveyId) {
        Session session = this.factory.getObject().getCurrentSession();
        Survey s = session.get(Survey.class, surveyId);
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
    }

    @Override
    public Map<String, Object> getSurveyResults(Long surveyId) {
        Map<String, Object> detail = getSurveyDetail(surveyId);
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
                // Đếm số lượt chọn cho từng option
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
            } else if ("RATING".equalsIgnoreCase(type)) {
                // Đếm số lượt chọn cho từng mức rating (giả sử 1-5)
                List<Map<String, Object>> ratingStats = new ArrayList<>();
                for (int i = 1; i <= 5; i++) {
                    Long count = session.createQuery(
                        "SELECT COUNT(rd) FROM ResponseDetail rd WHERE rd.questionId.questionId = :qid AND rd.ratingValue = :val", Long.class)
                        .setParameter("qid", q.getQuestionId())
                        .setParameter("val", i)
                        .uniqueResult();
                    Map<String, Object> rate = new HashMap<>();
                    rate.put("ratingValue", i);
                    rate.put("count", count != null ? count : 0);
                    ratingStats.add(rate);
                }
                qResult.put("ratings", ratingStats);
            } else if ("TEXT".equalsIgnoreCase(type)) {
                // Đếm số lượt trả lời (có thể lấy thêm danh sách text nếu muốn)
                List<String> textAnswers = session.createQuery(
                    "SELECT rd.textAnswer FROM ResponseDetail rd WHERE rd.questionId.questionId = :qid AND rd.textAnswer IS NOT NULL", String.class)
                    .setParameter("qid", q.getQuestionId())
                    .getResultList();
                qResult.put("textAnswers", textAnswers);    
                Long count = session.createQuery(
                    "SELECT COUNT(rd) FROM ResponseDetail rd WHERE rd.questionId.questionId = :qid AND rd.textAnswer IS NOT NULL", Long.class)
                    .setParameter("qid", q.getQuestionId())
                    .uniqueResult();
                qResult.put("textCount", count != null ? count : 0);
            }
            results.add(qResult);
        }
        return Map.of("results", results);
    }

    @Override
    public List<Survey> getAvailableSurveysForUser() {
        Session session = this.factory.getObject().getCurrentSession();
        return session.createQuery("FROM Survey WHERE status = 'ACTIVE'", Survey.class).getResultList();
    }

    @Override
    public Map<String, Object> getSurveyDetailForUser(Long surveyId) {
        return getSurveyDetail(surveyId);
    }

    @Override
    public boolean respondSurvey(Long surveyId, Long userId, SurveyResponse response) {
        Session session = this.factory.getObject().getCurrentSession();
        User u = session.get(User.class, userId);
        // Kiểm tra đã tồn tại phản hồi chưa (1 user chỉ được trả lời 1 lần)
        Long count = session.createQuery(
            "SELECT COUNT(r) FROM SurveyResponse r WHERE r.surveyId.surveyId = :sid AND r.residentId.id = :uid", Long.class)
            .setParameter("sid", surveyId)
            .setParameter("uid", u.getId())
            .uniqueResult();
        if (count != null && count > 0) return false;
        session.save(response);
        return true;
    }
}
