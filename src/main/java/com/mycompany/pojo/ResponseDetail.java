/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author baoto
 */
@Entity
@Table(name = "response_detail")
@NamedQueries({
    @NamedQuery(name = "ResponseDetail.findAll", query = "SELECT r FROM ResponseDetail r"),
    @NamedQuery(name = "ResponseDetail.findByDetailId", query = "SELECT r FROM ResponseDetail r WHERE r.detailId = :detailId"),
    @NamedQuery(name = "ResponseDetail.findByRatingValue", query = "SELECT r FROM ResponseDetail r WHERE r.ratingValue = :ratingValue")})
public class ResponseDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "detail_id")
    private Long detailId;
    @Lob
    @Size(max = 65535)
    @Column(name = "text_answer")
    private String textAnswer;
    @Column(name = "rating_value")
    private Integer ratingValue;
    @JoinColumn(name = "option_id", referencedColumnName = "option_id")
    @ManyToOne
    private QuestionOption optionId;
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @ManyToOne(optional = false)
    private SurveyQuestion questionId;
    @JoinColumn(name = "response_id", referencedColumnName = "response_id")
    @ManyToOne(optional = false)
    private SurveyResponse responseId;

    public ResponseDetail() {
    }

    public ResponseDetail(Long detailId) {
        this.detailId = detailId;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

    public Integer getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Integer ratingValue) {
        this.ratingValue = ratingValue;
    }

    public QuestionOption getOptionId() {
        return optionId;
    }

    public void setOptionId(QuestionOption optionId) {
        this.optionId = optionId;
    }

    public SurveyQuestion getQuestionId() {
        return questionId;
    }

    public void setQuestionId(SurveyQuestion questionId) {
        this.questionId = questionId;
    }

    public SurveyResponse getResponseId() {
        return responseId;
    }

    public void setResponseId(SurveyResponse responseId) {
        this.responseId = responseId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detailId != null ? detailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResponseDetail)) {
            return false;
        }
        ResponseDetail other = (ResponseDetail) object;
        if ((this.detailId == null && other.detailId != null) || (this.detailId != null && !this.detailId.equals(other.detailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.ResponseDetail[ detailId=" + detailId + " ]";
    }
    
}
