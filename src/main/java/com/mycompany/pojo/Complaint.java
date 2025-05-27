/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author baoto
 */
@Entity
@Table(name = "complaint")
@NamedQueries({
    @NamedQuery(name = "Complaint.findAll", query = "SELECT c FROM Complaint c"),
    @NamedQuery(name = "Complaint.findByComplaintId", query = "SELECT c FROM Complaint c WHERE c.complaintId = :complaintId"),
    @NamedQuery(name = "Complaint.findByTitle", query = "SELECT c FROM Complaint c WHERE c.title = :title"),
    @NamedQuery(name = "Complaint.findByImageProof", query = "SELECT c FROM Complaint c WHERE c.imageProof = :imageProof"),
    @NamedQuery(name = "Complaint.findBySubmittedDate", query = "SELECT c FROM Complaint c WHERE c.submittedDate = :submittedDate"),
    @NamedQuery(name = "Complaint.findByStatus", query = "SELECT c FROM Complaint c WHERE c.status = :status"),
    @NamedQuery(name = "Complaint.findByResolvedDate", query = "SELECT c FROM Complaint c WHERE c.resolvedDate = :resolvedDate")})
public class Complaint implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "complaint_id")
    private Long complaintId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "content")
    private String content;
    @Size(max = 255)
    @Column(name = "image_proof")
    private String imageProof;
    @Column(name = "submitted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedDate;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @Column(name = "resolved_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resolvedDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "resolution_note")
    private String resolutionNote;
    @JoinColumn(name = "resident_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonIgnore
    private User residentId;
    @JoinColumn(name = "resolved_by_admin_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private User resolvedByAdminId;

    public Complaint() {
    }

    public Complaint(Long complaintId) {
        this.complaintId = complaintId;
    }

    public Complaint(Long complaintId, String title, String content) {
        this.complaintId = complaintId;
        this.title = title;
        this.content = content;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageProof() {
        return imageProof;
    }

    public void setImageProof(String imageProof) {
        this.imageProof = imageProof;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(Date resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public String getResolutionNote() {
        return resolutionNote;
    }

    public void setResolutionNote(String resolutionNote) {
        this.resolutionNote = resolutionNote;
    }

    @JsonIgnore
    public User getResidentId() {
        return residentId;
    }

    public void setResidentId(User residentId) {
        this.residentId = residentId;
    }

    @JsonIgnore
    public User getResolvedByAdminId() {
        return resolvedByAdminId;
    }

    public void setResolvedByAdminId(User resolvedByAdminId) {
        this.resolvedByAdminId = resolvedByAdminId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (complaintId != null ? complaintId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Complaint)) {
            return false;
        }
        Complaint other = (Complaint) object;
        if ((this.complaintId == null && other.complaintId != null) || (this.complaintId != null && !this.complaintId.equals(other.complaintId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.Complaint[ complaintId=" + complaintId + " ]";
    }
    
}
