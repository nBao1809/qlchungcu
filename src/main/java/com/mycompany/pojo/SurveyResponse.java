/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author baoto
 */
@Entity
@Table(name = "survey_response")
@NamedQueries({
    @NamedQuery(name = "SurveyResponse.findAll", query = "SELECT s FROM SurveyResponse s"),
    @NamedQuery(name = "SurveyResponse.findByResponseId", query = "SELECT s FROM SurveyResponse s WHERE s.responseId = :responseId"),
    @NamedQuery(name = "SurveyResponse.findBySubmittedAt", query = "SELECT s FROM SurveyResponse s WHERE s.submittedAt = :submittedAt")})
public class SurveyResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "response_id")
    private Long responseId;
    @Column(name = "submitted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedAt;
    @JoinColumn(name = "survey_id", referencedColumnName = "survey_id")
    @ManyToOne(optional = false)
    private Survey surveyId;
    @JoinColumn(name = "resident_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User residentId;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "responseId")
    private Collection<ResponseDetail> responseDetailCollection;

    public SurveyResponse() {
    }

    public SurveyResponse(Long responseId) {
        this.responseId = responseId;
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Survey getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Survey surveyId) {
        this.surveyId = surveyId;
    }

    public User getResidentId() {
        return residentId;
    }

    public void setResidentId(User residentId) {
        this.residentId = residentId;
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
        hash += (responseId != null ? responseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SurveyResponse)) {
            return false;
        }
        SurveyResponse other = (SurveyResponse) object;
        if ((this.responseId == null && other.responseId != null) || (this.responseId != null && !this.responseId.equals(other.responseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.SurveyResponse[ responseId=" + responseId + " ]";
    }
    
}
