/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
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
import jakarta.persistence.Lob;
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
@Table(name = "fee_type")
@NamedQueries({
    @NamedQuery(name = "FeeType.findAll", query = "SELECT f FROM FeeType f"),
    @NamedQuery(name = "FeeType.findByFeeTypeId", query = "SELECT f FROM FeeType f WHERE f.feeTypeId = :feeTypeId"),
    @NamedQuery(name = "FeeType.findByFeeName", query = "SELECT f FROM FeeType f WHERE f.feeName = :feeName"),
    @NamedQuery(name = "FeeType.findByAmountPerUnit", query = "SELECT f FROM FeeType f WHERE f.amountPerUnit = :amountPerUnit"),
    @NamedQuery(name = "FeeType.findByUnit", query = "SELECT f FROM FeeType f WHERE f.unit = :unit"),
    @NamedQuery(name = "FeeType.findByCreatedAt", query = "SELECT f FROM FeeType f WHERE f.createdAt = :createdAt"),
    @NamedQuery(name = "FeeType.findByUpdatedAt", query = "SELECT f FROM FeeType f WHERE f.updatedAt = :updatedAt"),
    @NamedQuery(name = "FeeType.findByStatus", query = "SELECT f FROM FeeType f WHERE f.status = :status")})
public class FeeType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fee_type_id")
    private Long feeTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "fee_name")
    private String feeName;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount_per_unit")
    private BigDecimal amountPerUnit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "unit")
    private String unit;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "status")
    private Boolean status;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feeTypeId")
    private Collection<BillDetail> billDetailCollection;

    public FeeType() {
    }

    public FeeType(Long feeTypeId) {
        this.feeTypeId = feeTypeId;
    }

    public FeeType(Long feeTypeId, String feeName, BigDecimal amountPerUnit, String unit) {
        this.feeTypeId = feeTypeId;
        this.feeName = feeName;
        this.amountPerUnit = amountPerUnit;
        this.unit = unit;
    }

    public Long getFeeTypeId() {
        return feeTypeId;
    }

    public void setFeeTypeId(Long feeTypeId) {
        this.feeTypeId = feeTypeId;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmountPerUnit() {
        return amountPerUnit;
    }

    public void setAmountPerUnit(BigDecimal amountPerUnit) {
        this.amountPerUnit = amountPerUnit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public Collection<BillDetail> getBillDetailCollection() {
        return billDetailCollection;
    }

    public void setBillDetailCollection(Collection<BillDetail> billDetailCollection) {
        this.billDetailCollection = billDetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (feeTypeId != null ? feeTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FeeType)) {
            return false;
        }
        FeeType other = (FeeType) object;
        if ((this.feeTypeId == null && other.feeTypeId != null) || (this.feeTypeId != null && !this.feeTypeId.equals(other.feeTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.FeeType[ feeTypeId=" + feeTypeId + " ]";
    }
    
}
