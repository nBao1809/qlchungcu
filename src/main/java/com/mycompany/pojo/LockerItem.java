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
@Table(name = "locker_item")
@NamedQueries({
    @NamedQuery(name = "LockerItem.findAll", query = "SELECT l FROM LockerItem l"),
    @NamedQuery(name = "LockerItem.findByItemId", query = "SELECT l FROM LockerItem l WHERE l.itemId = :itemId"),
    @NamedQuery(name = "LockerItem.findByItemName", query = "SELECT l FROM LockerItem l WHERE l.itemName = :itemName"),
    @NamedQuery(name = "LockerItem.findByReceivedDate", query = "SELECT l FROM LockerItem l WHERE l.receivedDate = :receivedDate"),
    @NamedQuery(name = "LockerItem.findByStatus", query = "SELECT l FROM LockerItem l WHERE l.status = :status"),
    @NamedQuery(name = "LockerItem.findByDeliveredDate", query = "SELECT l FROM LockerItem l WHERE l.deliveredDate = :deliveredDate")})
public class LockerItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "item_id")
    private Long itemId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "item_name")
    private String itemName;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Column(name = "received_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedDate;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @Column(name = "delivered_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveredDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "resident_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User residentId;
    @JoinColumn(name = "received_by_admin_id", referencedColumnName = "id")
    @ManyToOne
    private User receivedByAdminId;

    public LockerItem() {
    }

    public LockerItem(Long itemId) {
        this.itemId = itemId;
    }

    public LockerItem(Long itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @JsonIgnore
    public User getResidentId() {
        return residentId;
    }

    public void setResidentId(User residentId) {
        this.residentId = residentId;
    }

    @JsonIgnore
    public User getReceivedByAdminId() {
        return receivedByAdminId;
    }

    public void setReceivedByAdminId(User receivedByAdminId) {
        this.receivedByAdminId = receivedByAdminId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemId != null ? itemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LockerItem)) {
            return false;
        }
        LockerItem other = (LockerItem) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.LockerItem[ itemId=" + itemId + " ]";
    }
    
}
