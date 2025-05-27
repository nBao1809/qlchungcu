/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author baoto
 */
@Entity
@Table(name = "bill_detail")
@NamedQueries({
    @NamedQuery(name = "BillDetail.findAll", query = "SELECT b FROM BillDetail b"),
    @NamedQuery(name = "BillDetail.findByBillDetailId", query = "SELECT b FROM BillDetail b WHERE b.billDetailId = :billDetailId"),
    @NamedQuery(name = "BillDetail.findByQuantity", query = "SELECT b FROM BillDetail b WHERE b.quantity = :quantity"),
    @NamedQuery(name = "BillDetail.findByUnitPrice", query = "SELECT b FROM BillDetail b WHERE b.unitPrice = :unitPrice"),
    @NamedQuery(name = "BillDetail.findByAmount", query = "SELECT b FROM BillDetail b WHERE b.amount = :amount")})
public class BillDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bill_detail_id")
    private Long billDetailId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private BigDecimal quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;
    @Lob
    @Size(max = 65535)
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "bill_id", referencedColumnName = "bill_id")
    @ManyToOne(optional = false)
    private Bill billId;
    @JoinColumn(name = "fee_type_id", referencedColumnName = "fee_type_id")
    @ManyToOne(optional = false)
    private FeeType feeTypeId;

    public BillDetail() {
    }

    public BillDetail(Long billDetailId) {
        this.billDetailId = billDetailId;
    }

    public BillDetail(Long billDetailId, BigDecimal quantity, BigDecimal unitPrice, BigDecimal amount) {
        this.billDetailId = billDetailId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }

    public Long getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(Long billDetailId) {
        this.billDetailId = billDetailId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Bill getBillId() {
        return billId;
    }

    public void setBillId(Bill billId) {
        this.billId = billId;
    }

    public FeeType getFeeTypeId() {
        return feeTypeId;
    }

    public void setFeeTypeId(FeeType feeTypeId) {
        this.feeTypeId = feeTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (billDetailId != null ? billDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BillDetail)) {
            return false;
        }
        BillDetail other = (BillDetail) object;
        if ((this.billDetailId == null && other.billDetailId != null) || (this.billDetailId != null && !this.billDetailId.equals(other.billDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.BillDetail[ billDetailId=" + billDetailId + " ]";
    }
    
}
