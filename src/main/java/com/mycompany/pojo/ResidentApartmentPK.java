/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author baoto
 */
@Embeddable
public class ResidentApartmentPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "resident_id")
    private long residentId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "apartment_id")
    private long apartmentId;

    public ResidentApartmentPK() {
    }

    public ResidentApartmentPK(long residentId, long apartmentId) {
        this.residentId = residentId;
        this.apartmentId = apartmentId;
    }

    public long getResidentId() {
        return residentId;
    }

    public void setResidentId(long residentId) {
        this.residentId = residentId;
    }

    public long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(long apartmentId) {
        this.apartmentId = apartmentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) residentId;
        hash += (int) apartmentId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResidentApartmentPK)) {
            return false;
        }
        ResidentApartmentPK other = (ResidentApartmentPK) object;
        if (this.residentId != other.residentId) {
            return false;
        }
        if (this.apartmentId != other.apartmentId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.ResidentApartmentPK[ residentId=" + residentId + ", apartmentId=" + apartmentId + " ]";
    }
    
}
