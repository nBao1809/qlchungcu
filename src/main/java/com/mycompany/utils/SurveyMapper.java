package com.mycompany.utils;

import java.util.stream.Collectors;

import com.mycompany.dto.QuestionOptionDTO;
import com.mycompany.dto.ResponseDetailDTO;
import com.mycompany.dto.SurveyDTO;
import com.mycompany.dto.SurveyQuestionDTO;
import com.mycompany.dto.SurveyResponseDTO;
import com.mycompany.pojo.QuestionOption;
import com.mycompany.pojo.ResponseDetail;
import com.mycompany.pojo.Survey;
import com.mycompany.pojo.SurveyQuestion;
import com.mycompany.pojo.SurveyResponse;

public class SurveyMapper {
    
    public static SurveyDTO toDTO(Survey survey) {
        if (survey == null) return null;
        
        SurveyDTO dto = new SurveyDTO();
        dto.setSurveyId(survey.getSurveyId());
        dto.setTitle(survey.getTitle());
        dto.setDescription(survey.getDescription());
        dto.setStartDate(survey.getStartDate());
        dto.setEndDate(survey.getEndDate());
        dto.setCreatedAt(survey.getCreatedAt());
        dto.setStatus(survey.getStatus());
        dto.setCreatedByAdminId(survey.getCreatedByAdminId() != null ? survey.getCreatedByAdminId().getId() : null);
        
        if (survey.getSurveyQuestionCollection() != null) {
            dto.setQuestions(survey.getSurveyQuestionCollection().stream()
                .map(SurveyMapper::toQuestionDTO)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    public static SurveyQuestionDTO toQuestionDTO(SurveyQuestion question) {
        if (question == null) return null;
        
        SurveyQuestionDTO dto = new SurveyQuestionDTO();
        dto.setQuestionId(question.getQuestionId());
        dto.setQuestionText(question.getQuestionText());
        dto.setQuestionType(question.getQuestionType());
        dto.setRequired(question.getRequired());
        dto.setOrderNumber(question.getOrderNumber());
        
        if (question.getQuestionOptionCollection() != null) {
            dto.setOptions(question.getQuestionOptionCollection().stream()
                .map(SurveyMapper::toOptionDTO)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    public static QuestionOptionDTO toOptionDTO(QuestionOption option) {
        if (option == null) return null;
        
        QuestionOptionDTO dto = new QuestionOptionDTO();
        dto.setOptionId(option.getOptionId());
        dto.setOptionText(option.getOptionText());
        dto.setOrderNumber(option.getOrderNumber());
        
        return dto;
    }
    
    public static SurveyResponseDTO toResponseDTO(SurveyResponse response) {
        if (response == null) return null;
        
        SurveyResponseDTO dto = new SurveyResponseDTO();
        dto.setResponseId(response.getResponseId());
        dto.setUserId(response.getResidentId() != null ? response.getResidentId().getId() : null);
        dto.setSubmittedAt(response.getSubmittedAt());
        
        if (response.getResponseDetailCollection() != null) {
            dto.setDetails(response.getResponseDetailCollection().stream()
                .map(SurveyMapper::toResponseDetailDTO)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    public static ResponseDetailDTO toResponseDetailDTO(ResponseDetail detail) {
        if (detail == null) return null;
        
        ResponseDetailDTO dto = new ResponseDetailDTO();
        dto.setQuestionId(detail.getQuestionId() != null ? detail.getQuestionId().getQuestionId() : null);
        dto.setTextResponse(detail.getTextAnswer());
        dto.setRatingValue(detail.getRatingValue());
        dto.setOptionId(detail.getOptionId() != null ? detail.getOptionId().getOptionId() : null);
        
        return dto;
    }
}
