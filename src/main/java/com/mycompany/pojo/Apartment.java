/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "apartment")
@NamedQueries({
    @NamedQuery(name = "Apartment.findAll", query = "SELECT a FROM Apartment a"),
    @NamedQuery(name = "Apartment.findByApartmentId", query = "SELECT a FROM Apartment a WHERE a.apartmentId = :apartmentId"),
    @NamedQuery(name = "Apartment.findByApartmentCode", query = "SELECT a FROM Apartment a WHERE a.apartmentCode = :apartmentCode"),
    @NamedQuery(name = "Apartment.findByFloor", query = "SELECT a FROM Apartment a WHERE a.floor = :floor"),
    @NamedQuery(name = "Apartment.findByBlock", query = "SELECT a FROM Apartment a WHERE a.block = :block"),
    @NamedQuery(name = "Apartment.findByStatus", query = "SELECT a FROM Apartment a WHERE a.status = :status")})
public class Apartment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "apartment_id")
    private Long apartmentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "apartment_code")
    private String apartmentCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "floor")
    private int floor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "block")
    private String block;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartmentId")
    private Collection<Bill> billCollection;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartment")
    private Collection<ResidentApartment> residentApartmentCollection;

    public Apartment() {
    }

    public Apartment(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Apartment(Long apartmentId, String apartmentCode, int floor, String block) {
        this.apartmentId = apartmentId;
        this.apartmentCode = apartmentCode;
        this.floor = floor;
        this.block = block;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getApartmentCode() {
        return apartmentCode;
    }

    public void setApartmentCode(String apartmentCode) {
        this.apartmentCode = apartmentCode;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnore
    public Collection<Bill> getBillCollection() {
        return billCollection;
    }

    @JsonIgnore
    public Collection<ResidentApartment> getResidentApartmentCollection() {
        return residentApartmentCollection;
    }

    public void setResidentApartmentCollection(Collection<ResidentApartment> residentApartmentCollection) {
        this.residentApartmentCollection = residentApartmentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (apartmentId != null ? apartmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apartment)) {
            return false;
        }
        Apartment other = (Apartment) object;
        if ((this.apartmentId == null && other.apartmentId != null) || (this.apartmentId != null && !this.apartmentId.equals(other.apartmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.Apartment[ apartmentId=" + apartmentId + " ]";
    }
    
}
