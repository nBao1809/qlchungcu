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
@Table(name = "vehicle")
@NamedQueries({
    @NamedQuery(name = "Vehicle.findAll", query = "SELECT v FROM Vehicle v"),
    @NamedQuery(name = "Vehicle.findByVehicleId", query = "SELECT v FROM Vehicle v WHERE v.vehicleId = :vehicleId"),
    @NamedQuery(name = "Vehicle.findByLicensePlate", query = "SELECT v FROM Vehicle v WHERE v.licensePlate = :licensePlate"),
    @NamedQuery(name = "Vehicle.findByVehicleType", query = "SELECT v FROM Vehicle v WHERE v.vehicleType = :vehicleType"),
    @NamedQuery(name = "Vehicle.findByCreatedAt", query = "SELECT v FROM Vehicle v WHERE v.createdAt = :createdAt"),
    @NamedQuery(name = "Vehicle.findByUpdatedAt", query = "SELECT v FROM Vehicle v WHERE v.updatedAt = :updatedAt"),
    @NamedQuery(name = "Vehicle.findByStatus", query = "SELECT v FROM Vehicle v WHERE v.status = :status")})
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vehicle_id")
    private Long vehicleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "license_plate")
    private String licensePlate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "vehicle_type")
    private String vehicleType;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "status")
    private Boolean status;
    @JoinColumn(name = "relative_id", referencedColumnName = "relative_id")
    @ManyToOne
    private Relative relativeId;
    @JoinColumn(name = "resident_id", referencedColumnName = "id")
    @ManyToOne
    private User residentId;

    public Vehicle() {
    }

    public Vehicle(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Vehicle(Long vehicleId, String licensePlate, String vehicleType) {
        this.vehicleId = vehicleId;
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
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
    public Relative getRelativeId() {
        return relativeId;
    }

    public void setRelativeId(Relative relativeId) {
        this.relativeId = relativeId;
    }

    @JsonIgnore
    public User getResidentId() {
        return residentId;
    }

    public void setResidentId(User residentId) {
        this.residentId = residentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vehicleId != null ? vehicleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehicle)) {
            return false;
        }
        Vehicle other = (Vehicle) object;
        if ((this.vehicleId == null && other.vehicleId != null) || (this.vehicleId != null && !this.vehicleId.equals(other.vehicleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.Vehicle[ vehicleId=" + vehicleId + " ]";
    }
    
}
