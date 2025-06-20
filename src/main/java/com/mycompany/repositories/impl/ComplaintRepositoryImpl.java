/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repositories.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.pojo.Complaint;
import com.mycompany.pojo.User;
import com.mycompany.repositories.ComplaintRepository;

/**
 *
 * @author baoto
 */
@Repository
@Transactional
public class ComplaintRepositoryImpl implements ComplaintRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Complaint> getAllComplaints() {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query<Complaint> q = s.createNamedQuery("Complaint.findAll", Complaint.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Complaint getComplaintById(Long id) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query<Complaint> q = s.createNamedQuery("Complaint.findByComplaintId", Complaint.class);
            q.setParameter("complaintId", id);
            return q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Complaint> getComplaintsByUser(User user) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query<Complaint> q = s.createQuery("FROM Complaint c WHERE c.residentId = :user", Complaint.class);
            q.setParameter("user", user);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Complaint addComplaint(Complaint complaint) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.persist(complaint);
            return complaint;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void updateComplaint(Complaint complaint) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.update(complaint);
        } catch (Exception e) {
            // Có thể log lỗi ở đây nếu cần
        }
    }
}
