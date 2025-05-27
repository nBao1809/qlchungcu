package com.mycompany.services;

import java.util.List;
import java.util.Map;

import com.mycompany.pojo.Survey;

public interface SurveyService {
    List<Survey> getAllSurveys();

    Survey addSurvey(Map<String, Object> params);

    Survey updateSurveyStatus(Long surveyId, Map<String, String> params);

    Map<String, Object> getSurveyDetail(Long surveyId);

    Map<String, Object> getSurveyResults(Long surveyId);

    List<Survey> getAvailableSurveysForUser();

    Map<String, Object> getSurveyDetailForUser(Long surveyId);

    boolean respondSurvey(Long surveyId, Map<String, Object> params);
}
