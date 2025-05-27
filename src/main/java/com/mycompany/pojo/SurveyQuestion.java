/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author baoto
 */
@Entity
@Table(name = "survey_question")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "questionId")
@NamedQueries({
    @NamedQuery(name = "SurveyQuestion.findAll", query = "SELECT s FROM SurveyQuestion s"),
    @NamedQuery(name = "SurveyQuestion.findByQuestionId", query = "SELECT s FROM SurveyQuestion s WHERE s.questionId = :questionId"),
    @NamedQuery(name = "SurveyQuestion.findByQuestionType", query = "SELECT s FROM SurveyQuestion s WHERE s.questionType = :questionType"),
    @NamedQuery(name = "SurveyQuestion.findByRequired", query = "SELECT s FROM SurveyQuestion s WHERE s.required = :required"),
    @NamedQuery(name = "SurveyQuestion.findByOrderNumber", query = "SELECT s FROM SurveyQuestion s WHERE s.orderNumber = :orderNumber")})
public class SurveyQuestion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "question_id")
    private Long questionId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "question_text")
    private String questionText;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "question_type")
    private String questionType;
    @Column(name = "required")
    private Boolean required;
    @Column(name = "order_number")
    private Integer orderNumber;    @JsonBackReference("survey-questions")
    @JoinColumn(name = "survey_id", referencedColumnName = "survey_id")
    @ManyToOne(optional = false)
    private Survey surveyId;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionId")
    private Collection<QuestionOption> questionOptionCollection;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionId")
    private Collection<ResponseDetail> responseDetailCollection;

    public SurveyQuestion() {
    }

    public SurveyQuestion(Long questionId) {
        this.questionId = questionId;
    }

    public SurveyQuestion(Long questionId, String questionText, String questionType) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionType = questionType;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Survey getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Survey surveyId) {
        this.surveyId = surveyId;
    }

    public Collection<QuestionOption> getQuestionOptionCollection() {
        return questionOptionCollection;
    }

    public void setQuestionOptionCollection(Collection<QuestionOption> questionOptionCollection) {
        this.questionOptionCollection = questionOptionCollection;
    }

    public Collection<ResponseDetail> getResponseDetailCollection() {
        return responseDetailCollection;
    }

    public void setResponseDetailCollection(Collection<ResponseDetail> responseDetailCollection) {
        this.responseDetailCollection = responseDetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SurveyQuestion)) {
            return false;
        }
        SurveyQuestion other = (SurveyQuestion) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.SurveyQuestion[ questionId=" + questionId + " ]";
    }
    
}
