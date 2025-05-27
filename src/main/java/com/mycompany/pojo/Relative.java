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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author baoto
 */
@Entity
@Table(name = "relative")
@NamedQueries({
    @NamedQuery(name = "Relative.findAll", query = "SELECT r FROM Relative r"),
    @NamedQuery(name = "Relative.findByRelativeId", query = "SELECT r FROM Relative r WHERE r.relativeId = :relativeId"),
    @NamedQuery(name = "Relative.findByFullName", query = "SELECT r FROM Relative r WHERE r.fullName = :fullName"),
    @NamedQuery(name = "Relative.findByRelationship", query = "SELECT r FROM Relative r WHERE r.relationship = :relationship"),
    @NamedQuery(name = "Relative.findByPhone", query = "SELECT r FROM Relative r WHERE r.phone = :phone"),
    @NamedQuery(name = "Relative.findByCccd", query = "SELECT r FROM Relative r WHERE r.cccd = :cccd"),
    @NamedQuery(name = "Relative.findByHasAccessCard", query = "SELECT r FROM Relative r WHERE r.hasAccessCard = :hasAccessCard"),
    @NamedQuery(name = "Relative.findByHasVehicleCard", query = "SELECT r FROM Relative r WHERE r.hasVehicleCard = :hasVehicleCard"),
    @NamedQuery(name = "Relative.findByCreatedAt", query = "SELECT r FROM Relative r WHERE r.createdAt = :createdAt"),
    @NamedQuery(name = "Relative.findByUpdatedAt", query = "SELECT r FROM Relative r WHERE r.updatedAt = :updatedAt"),
    @NamedQuery(name = "Relative.findByStatus", query = "SELECT r FROM Relative r WHERE r.status = :status"),
    @NamedQuery(name = "Relative.findByResidentId", query = "SELECT r FROM Relative r WHERE r.residentId.id = :residentId")})
public class Relative implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "relative_id")
    private Long relativeId;

    @Basic(optional = false)
    @NotNull(message = "Full name is required")
    @Size(min = 1, max = 100, message = "Full name must be between 1 and 100 characters")
    @Column(name = "full_name")
    private String fullName;

    @Basic(optional = false)
    @NotNull(message = "Relationship is required")
    @Size(min = 1, max = 50, message = "Relationship must be between 1 and 50 characters")
    @Column(name = "relationship")
    private String relationship;

    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Column(name = "phone")
    private String phone;

    @Basic(optional = false)
    @NotNull(message = "CCCD is required")
    @Pattern(regexp = "^\\d{12}$", message = "CCCD must be 12 digits")
    @Column(name = "cccd")
    private String cccd;

    @Column(name = "has_access_card")
    private Boolean hasAccessCard = false;

    @Column(name = "has_vehicle_card") 
    private Boolean hasVehicleCard = false;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @Column(name = "status")
    private Boolean status = true;

    @JsonIgnore
    @OneToMany(mappedBy = "relativeId")
    private Collection<Vehicle> vehicleCollection;

    @JoinColumn(name = "resident_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonIgnore
    private User residentId;

    public Relative() {
    }

    public Relative(Long relativeId) {
        this.relativeId = relativeId;
    }

    public Relative(Long relativeId, String fullName, String relationship, String phone, String cccd) {
        this.relativeId = relativeId;
        this.fullName = fullName;
        this.relationship = relationship;
        this.phone = phone;
        this.cccd = cccd;
    }

    public Long getRelativeId() {
        return relativeId;
    }

    public void setRelativeId(Long relativeId) {
        this.relativeId = relativeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public Boolean getHasAccessCard() {
        return hasAccessCard;
    }

    public void setHasAccessCard(Boolean hasAccessCard) {
        this.hasAccessCard = hasAccessCard;
    }

    public Boolean getHasVehicleCard() {
        return hasVehicleCard;
    }

    public void setHasVehicleCard(Boolean hasVehicleCard) {
        this.hasVehicleCard = hasVehicleCard;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @JsonIgnore
    public Collection<Vehicle> getVehicleCollection() {
        return vehicleCollection;
    }

    @JsonIgnore
    public User getResidentId() {
        return residentId;
    }

    public void setVehicleCollection(Collection<Vehicle> vehicleCollection) {
        this.vehicleCollection = vehicleCollection;
    }

    public void setResidentId(User residentId) {
        this.residentId = residentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (relativeId != null ? relativeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relative)) {
            return false;
        }
        Relative other = (Relative) object;
        if ((this.relativeId == null && other.relativeId != null) || (this.relativeId != null && !this.relativeId.equals(other.relativeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.Relative[ relativeId=" + relativeId + " ]";
    }
    
}
