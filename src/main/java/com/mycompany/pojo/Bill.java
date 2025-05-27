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
import jakarta.persistence.JoinColumn;
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
@Table(name = "bill")
@NamedQueries({
    @NamedQuery(name = "Bill.findAll", query = "SELECT b FROM Bill b"),
    @NamedQuery(name = "Bill.findByBillId", query = "SELECT b FROM Bill b WHERE b.billId = :billId"),
    @NamedQuery(name = "Bill.findByMonth", query = "SELECT b FROM Bill b WHERE b.month = :month"),
    @NamedQuery(name = "Bill.findByYear", query = "SELECT b FROM Bill b WHERE b.year = :year"),
    @NamedQuery(name = "Bill.findByTotalAmount", query = "SELECT b FROM Bill b WHERE b.totalAmount = :totalAmount"),
    @NamedQuery(name = "Bill.findByPaymentStatus", query = "SELECT b FROM Bill b WHERE b.paymentStatus = :paymentStatus"),
    @NamedQuery(name = "Bill.findByPaymentMethod", query = "SELECT b FROM Bill b WHERE b.paymentMethod = :paymentMethod"),
    @NamedQuery(name = "Bill.findByPaymentDate", query = "SELECT b FROM Bill b WHERE b.paymentDate = :paymentDate"),
    @NamedQuery(name = "Bill.findByPaymentProof", query = "SELECT b FROM Bill b WHERE b.paymentProof = :paymentProof"),
    @NamedQuery(name = "Bill.findByCreatedAt", query = "SELECT b FROM Bill b WHERE b.createdAt = :createdAt"),
    @NamedQuery(name = "Bill.findByUpdatedAt", query = "SELECT b FROM Bill b WHERE b.updatedAt = :updatedAt")})
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bill_id")
    private Long billId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "month")
    private short month;
    @Basic(optional = false)
    @NotNull
    @Column(name = "year")
    private int year;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Size(max = 20)
    @Column(name = "payment_status")
    private String paymentStatus;
    @Size(max = 20)
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @Size(max = 255)
    @Column(name = "payment_proof")
    private String paymentProof;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "billId")
    private Collection<PaymentTransaction> paymentTransactionCollection;
    @JoinColumn(name = "apartment_id", referencedColumnName = "apartment_id")
    @ManyToOne(optional = false)
    private Apartment apartmentId;
    @JoinColumn(name = "paid_by_resident_id", referencedColumnName = "id")
    @ManyToOne
    private User paidByResidentId;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "billId")
    private Collection<BillDetail> billDetailCollection;

    public Bill() {
    }

    public Bill(Long billId) {
        this.billId = billId;
    }

    public Bill(Long billId, short month, int year, BigDecimal totalAmount) {
        this.billId = billId;
        this.month = month;
        this.year = year;
        this.totalAmount = totalAmount;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public short getMonth() {
        return month;
    }

    public void setMonth(short month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentProof() {
        return paymentProof;
    }

    public void setPaymentProof(String paymentProof) {
        this.paymentProof = paymentProof;
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

    @JsonIgnore
    public Collection<PaymentTransaction> getPaymentTransactionCollection() {
        return paymentTransactionCollection;
    }

    public void setPaymentTransactionCollection(Collection<PaymentTransaction> paymentTransactionCollection) {
        this.paymentTransactionCollection = paymentTransactionCollection;
    }

    public Apartment getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Apartment apartmentId) {
        this.apartmentId = apartmentId;
    }

    public User getPaidByResidentId() {
        return paidByResidentId;
    }

    public void setPaidByResidentId(User paidByResidentId) {
        this.paidByResidentId = paidByResidentId;
    }

    @JsonIgnore
    public Collection<BillDetail> getBillDetailCollection() {
        return billDetailCollection;
    }

    public void setBillDetailCollection(Collection<BillDetail> billDetailCollection) {
        this.billDetailCollection = billDetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (billId != null ? billId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bill)) {
            return false;
        }
        Bill other = (Bill) object;
        if ((this.billId == null && other.billId != null) || (this.billId != null && !this.billId.equals(other.billId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.Bill[ billId=" + billId + " ]";
    }
    
}
