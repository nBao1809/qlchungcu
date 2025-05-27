package com.mycompany.repositories;

import java.util.List;
import java.util.Map;

import com.mycompany.pojo.Survey;
import com.mycompany.pojo.SurveyResponse;

public interface SurveyRepository {
    List<Survey> getAllSurveys();
    Survey addSurvey(Survey survey);
    Survey updateSurveyStatus(Long surveyId, Map<String, String> params);
    Map<String, Object> getSurveyDetail(Long surveyId);
    Map<String, Object> getSurveyResults(Long surveyId);
    List<Survey> getAvailableSurveysForUser();
    Map<String, Object> getSurveyDetailForUser(Long surveyId);
    boolean respondSurvey(Long surveyId,Long userId,SurveyResponse responses);
}
