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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author baoto
 */
@Entity
@Table(name = "survey")
@NamedQueries({
    @NamedQuery(name = "Survey.findAll", query = "SELECT s FROM Survey s"),
    @NamedQuery(name = "Survey.findBySurveyId", query = "SELECT s FROM Survey s WHERE s.surveyId = :surveyId"),
    @NamedQuery(name = "Survey.findByTitle", query = "SELECT s FROM Survey s WHERE s.title = :title"),
    @NamedQuery(name = "Survey.findByStartDate", query = "SELECT s FROM Survey s WHERE s.startDate = :startDate"),
    @NamedQuery(name = "Survey.findByEndDate", query = "SELECT s FROM Survey s WHERE s.endDate = :endDate"),
    @NamedQuery(name = "Survey.findByCreatedAt", query = "SELECT s FROM Survey s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "Survey.findByStatus", query = "SELECT s FROM Survey s WHERE s.status = :status")})
public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "survey_id")
    private Long surveyId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "title")
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "surveyId")
    private Collection<SurveyQuestion> surveyQuestionCollection;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "surveyId")
    private Collection<SurveyResponse> surveyResponseCollection;
    @JsonIgnore
    @JoinColumn(name = "created_by_admin_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdByAdminId;

    public Survey() {
    }

    public Survey(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Survey(Long surveyId, String title, Date startDate, Date endDate) {
        this.surveyId = surveyId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Collection<SurveyQuestion> getSurveyQuestionCollection() {
        return surveyQuestionCollection;
    }

    public void setSurveyQuestionCollection(Collection<SurveyQuestion> surveyQuestionCollection) {
        this.surveyQuestionCollection = surveyQuestionCollection;
    }

    public Collection<SurveyResponse> getSurveyResponseCollection() {
        return surveyResponseCollection;
    }

    public void setSurveyResponseCollection(Collection<SurveyResponse> surveyResponseCollection) {
        this.surveyResponseCollection = surveyResponseCollection;
    }

    public User getCreatedByAdminId() {
        return createdByAdminId;
    }

    public void setCreatedByAdminId(User createdByAdminId) {
        this.createdByAdminId = createdByAdminId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (surveyId != null ? surveyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Survey)) {
            return false;
        }
        Survey other = (Survey) object;
        if ((this.surveyId == null && other.surveyId != null) || (this.surveyId != null && !this.surveyId.equals(other.surveyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.Survey[ surveyId=" + surveyId + " ]";
    }
    
}
