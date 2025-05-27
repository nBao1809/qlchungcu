/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.repositories.impl;

import java.math.BigDecimal;
import java.util.Date;
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
            
            // Set default values if needed
            if (bill.getTotalAmount() == null) {
                bill.setTotalAmount(BigDecimal.ZERO);
            }
            if (bill.getPaymentStatus() == null) {
                bill.setPaymentStatus("UNPAID");
            }
            
            Date now = new Date();
            if (bill.getCreatedAt() == null) {
                bill.setCreatedAt(now);
            }
            if (bill.getUpdatedAt() == null) {
                bill.setUpdatedAt(now);
            }
            
            s.persist(bill);
            return bill;
        } catch (Exception e) {
            throw new RuntimeException("Error saving bill to database", e);
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
            throw new RuntimeException("Error updating bill", e);
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

    @Override
    public Bill findByApartmentAndMonthAndYear(Long apartmentId, short month, int year) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query<Bill> q = s.createQuery(
                "FROM Bill b WHERE b.apartmentId.id = :apartmentId AND b.month = :month AND b.year = :year", 
                Bill.class);
            q.setParameter("apartmentId", apartmentId);
            q.setParameter("month", month);
            q.setParameter("year", year);
            return q.uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }
}
