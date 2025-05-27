/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author baoto
 */
@Entity
@Table(name = "resident_apartment")
@NamedQueries({
    @NamedQuery(name = "ResidentApartment.findAll", query = "SELECT r FROM ResidentApartment r"),
    @NamedQuery(name = "ResidentApartment.findByResidentId", query = "SELECT r FROM ResidentApartment r WHERE r.residentApartmentPK.residentId = :residentId"),
    @NamedQuery(name = "ResidentApartment.findByApartmentId", query = "SELECT r FROM ResidentApartment r WHERE r.residentApartmentPK.apartmentId = :apartmentId"),
    @NamedQuery(name = "ResidentApartment.findByRole", query = "SELECT r FROM ResidentApartment r WHERE r.role = :role"),
    @NamedQuery(name = "ResidentApartment.findByCreatedAt", query = "SELECT r FROM ResidentApartment r WHERE r.createdAt = :createdAt")})
public class ResidentApartment implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResidentApartmentPK residentApartmentPK;
    @Size(max = 20)
    @Column(name = "role")
    private String role;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "apartment_id", referencedColumnName = "apartment_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Apartment apartment;
    @JoinColumn(name = "resident_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public ResidentApartment() {
    }

    public ResidentApartment(ResidentApartmentPK residentApartmentPK) {
        this.residentApartmentPK = residentApartmentPK;
    }

    public ResidentApartment(long residentId, long apartmentId) {
        this.residentApartmentPK = new ResidentApartmentPK(residentId, apartmentId);
    }

    public ResidentApartmentPK getResidentApartmentPK() {
        return residentApartmentPK;
    }

    public void setResidentApartmentPK(ResidentApartmentPK residentApartmentPK) {
        this.residentApartmentPK = residentApartmentPK;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (residentApartmentPK != null ? residentApartmentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResidentApartment)) {
            return false;
        }
        ResidentApartment other = (ResidentApartment) object;
        if ((this.residentApartmentPK == null && other.residentApartmentPK != null) || (this.residentApartmentPK != null && !this.residentApartmentPK.equals(other.residentApartmentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.ResidentApartment[ residentApartmentPK=" + residentApartmentPK + " ]";
    }
    
}
