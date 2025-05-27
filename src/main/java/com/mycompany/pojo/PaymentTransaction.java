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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author baoto
 */
@Entity
@Table(name = "payment_transaction")
@NamedQueries({
    @NamedQuery(name = "PaymentTransaction.findAll", query = "SELECT p FROM PaymentTransaction p"),
    @NamedQuery(name = "PaymentTransaction.findByTransactionId", query = "SELECT p FROM PaymentTransaction p WHERE p.transactionId = :transactionId"),
    @NamedQuery(name = "PaymentTransaction.findByTransactionCode", query = "SELECT p FROM PaymentTransaction p WHERE p.transactionCode = :transactionCode"),
    @NamedQuery(name = "PaymentTransaction.findByPaymentMethod", query = "SELECT p FROM PaymentTransaction p WHERE p.paymentMethod = :paymentMethod"),
    @NamedQuery(name = "PaymentTransaction.findByAmount", query = "SELECT p FROM PaymentTransaction p WHERE p.amount = :amount"),
    @NamedQuery(name = "PaymentTransaction.findByTransactionDate", query = "SELECT p FROM PaymentTransaction p WHERE p.transactionDate = :transactionDate"),
    @NamedQuery(name = "PaymentTransaction.findByStatus", query = "SELECT p FROM PaymentTransaction p WHERE p.status = :status"),
    @NamedQuery(name = "PaymentTransaction.findByPaymentProof", query = "SELECT p FROM PaymentTransaction p WHERE p.paymentProof = :paymentProof"),
    @NamedQuery(name = "PaymentTransaction.findByReferenceNumber", query = "SELECT p FROM PaymentTransaction p WHERE p.referenceNumber = :referenceNumber")})
public class PaymentTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transaction_id")
    private Long transactionId;
    @Size(max = 100)
    @Column(name = "transaction_code")
    private String transactionCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "payment_method")
    private String paymentMethod;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @Size(max = 255)
    @Column(name = "payment_proof")
    private String paymentProof;
    @Size(max = 100)
    @Column(name = "reference_number")
    private String referenceNumber;
    @Lob
    @Size(max = 65535)
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "bill_id", referencedColumnName = "bill_id")
    @ManyToOne(optional = false)
    private Bill billId;

    public PaymentTransaction() {
    }

    public PaymentTransaction(Long transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentTransaction(Long transactionId, String paymentMethod, BigDecimal amount) {
        this.transactionId = transactionId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentProof() {
        return paymentProof;
    }

    public void setPaymentProof(String paymentProof) {
        this.paymentProof = paymentProof;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionId != null ? transactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentTransaction)) {
            return false;
        }
        PaymentTransaction other = (PaymentTransaction) object;
        if ((this.transactionId == null && other.transactionId != null) || (this.transactionId != null && !this.transactionId.equals(other.transactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.PaymentTransaction[ transactionId=" + transactionId + " ]";
    }
    
}
