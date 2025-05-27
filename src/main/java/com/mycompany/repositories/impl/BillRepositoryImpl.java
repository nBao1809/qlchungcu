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

import com.mycompany.pojo.Bill;
import com.mycompany.pojo.User;
import com.mycompany.repositories.BillRepository;


@Repository
@Transactional
public class BillRepositoryImpl implements BillRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Bill> getAllBills() {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query query = s.createNamedQuery("Bill.findAll", Bill.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Bill addBill(Bill bill) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.persist(bill);
            return bill;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Bill getBillById(Long billId) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query query = s.createNamedQuery("Bill.findByBillId", Bill.class);
            query.setParameter("billId", billId);
            return (Bill) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void updateBill(Bill bill) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.update(bill);
        } catch (Exception e) {
            // Có thể log lỗi ở đây nếu cần
        }
    }

    @Override
    public List<Bill> getBillsByResident(User user) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query<Bill> q = s.createQuery("FROM Bill b WHERE b.paidByResidentId = :user", Bill.class);
            q.setParameter("user", user);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Bill getBillByIdAndResident(Long billId, User user) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query<Bill> q = s.createQuery("FROM Bill b WHERE b.billId = :billId AND b.paidByResidentId = :user", Bill.class);
            q.setParameter("billId", billId);
            q.setParameter("user", user);
            return q.getResultStream().findFirst().orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
