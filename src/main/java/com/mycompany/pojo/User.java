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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author baoto
 */
@Entity
@Table(name = "user")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByFullName", query = "SELECT u FROM User u WHERE u.fullName = :fullName"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPhone", query = "SELECT u FROM User u WHERE u.phone = :phone"),
    @NamedQuery(name = "User.findByAvatar", query = "SELECT u FROM User u WHERE u.avatar = :avatar"),
    @NamedQuery(name = "User.findByRole", query = "SELECT u FROM User u WHERE u.role = :role"),
    @NamedQuery(name = "User.findByStatus", query = "SELECT u FROM User u WHERE u.status = :status"),
    @NamedQuery(name = "User.findByPasswordChanged", query = "SELECT u FROM User u WHERE u.passwordChanged = :passwordChanged"),
    @NamedQuery(name = "User.findByCreatedAt", query = "SELECT u FROM User u WHERE u.createdAt = :createdAt"),
    @NamedQuery(name = "User.findByUpdatedAt", query = "SELECT u FROM User u WHERE u.updatedAt = :updatedAt")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "full_name")
    private String fullName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "phone")
    private String phone;
    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "role")
    private String role;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @Column(name = "password_changed")
    private Boolean passwordChanged;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "residentId")
    private Collection<LockerItem> lockerItemCollection;
    @JsonIgnore
    @OneToMany(mappedBy = "receivedByAdminId")
    private Collection<LockerItem> lockerItemCollection1;
    @JsonIgnore
    @OneToMany(mappedBy = "paidByResidentId")
    private Collection<Bill> billCollection;
    @JsonIgnore
    @OneToMany(mappedBy = "residentId")
    private Collection<Vehicle> vehicleCollection;
    @JsonIgnore
    @OneToMany(mappedBy = "residentId")
    private Collection<SurveyResponse> surveyResponseCollection;
    @JsonIgnore
    @OneToMany(mappedBy = "residentId")
    private Collection<Notification> notificationCollection;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "residentId")
    private Collection<Complaint> complaintCollection;
    @JsonIgnore
    @OneToMany(mappedBy = "resolvedByAdminId")
    private Collection<Complaint> complaintCollection1;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdByAdminId")
    private Collection<Survey> surveyCollection;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<ResidentApartment> residentApartmentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "residentId")
    private Collection<Relative> relativeCollection;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String username, String password, String fullName, String email, String phone, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(Boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
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
    public Collection<LockerItem> getLockerItemCollection() {
        return lockerItemCollection;
    }

    public void setLockerItemCollection(Collection<LockerItem> lockerItemCollection) {
        this.lockerItemCollection = lockerItemCollection;
    }

    public Collection<LockerItem> getLockerItemCollection1() {
        return lockerItemCollection1;
    }

    public void setLockerItemCollection1(Collection<LockerItem> lockerItemCollection1) {
        this.lockerItemCollection1 = lockerItemCollection1;
    }

    @JsonIgnore
    public Collection<Bill> getBillCollection() {
        return billCollection;
    }

    public void setBillCollection(Collection<Bill> billCollection) {
        this.billCollection = billCollection;
    }

    @JsonIgnore
    public Collection<Vehicle> getVehicleCollection() {
        return vehicleCollection;
    }

    public void setVehicleCollection(Collection<Vehicle> vehicleCollection) {
        this.vehicleCollection = vehicleCollection;
    }

    public Collection<SurveyResponse> getSurveyResponseCollection() {
        return surveyResponseCollection;
    }

    public void setSurveyResponseCollection(Collection<SurveyResponse> surveyResponseCollection) {
        this.surveyResponseCollection = surveyResponseCollection;
    }

    public Collection<Notification> getNotificationCollection() {
        return notificationCollection;
    }

    public void setNotificationCollection(Collection<Notification> notificationCollection) {
        this.notificationCollection = notificationCollection;
    }

    @JsonIgnore
    public Collection<Complaint> getComplaintCollection() {
        return complaintCollection;
    }

    public void setComplaintCollection(Collection<Complaint> complaintCollection) {
        this.complaintCollection = complaintCollection;
    }

    public Collection<Complaint> getComplaintCollection1() {
        return complaintCollection1;
    }

    public void setComplaintCollection1(Collection<Complaint> complaintCollection1) {
        this.complaintCollection1 = complaintCollection1;
    }

    public Collection<Survey> getSurveyCollection() {
        return surveyCollection;
    }

    public void setSurveyCollection(Collection<Survey> surveyCollection) {
        this.surveyCollection = surveyCollection;
    }

    public Collection<ResidentApartment> getResidentApartmentCollection() {
        return residentApartmentCollection;
    }

    public void setResidentApartmentCollection(Collection<ResidentApartment> residentApartmentCollection) {
        this.residentApartmentCollection = residentApartmentCollection;
    }

    @JsonIgnore
    public Collection<Relative> getRelativeCollection() {
        return relativeCollection;
    }

    public void setRelativeCollection(Collection<Relative> relativeCollection) {
        this.relativeCollection = relativeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.User[ id=" + id + " ]";
    }
    
}
