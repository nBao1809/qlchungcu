package com.mycompany.repositories.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.pojo.BillDetail;
import com.mycompany.repositories.BillDetailRepository;
@Repository
@Transactional
public class BillDetailRepositoryImpl implements BillDetailRepository {
    @Autowired
    private LocalSessionFactoryBean factory;    @Override
    public BillDetail addBillDetail(BillDetail detail) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.persist(detail);
            return detail;
        } catch (Exception e) {
            throw new RuntimeException("Error saving bill detail", e);
        }
    }    @Override
    public List<BillDetail> getBillDetailsByBillId(Long billId) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query<BillDetail> q = s.createQuery("FROM BillDetail bd WHERE bd.billId.billId = :billId", BillDetail.class);
            q.setParameter("billId", billId);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
