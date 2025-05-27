package com.mycompany.dto;

import java.util.Date;
import java.util.List;

public class SurveyResponseDTO {
    private Long responseId;
    private Long userId;
    private Date submittedAt;
    private List<ResponseDetailDTO> details;

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }

    public List<ResponseDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<ResponseDetailDTO> details) {
        this.details = details;
    }
}
