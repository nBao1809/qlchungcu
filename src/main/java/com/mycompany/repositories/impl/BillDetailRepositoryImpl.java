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
    private LocalSessionFactoryBean factory;

    @Override
    public BillDetail addBillDetail(BillDetail detail) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(detail);
        return detail;
    }

    @Override
    public List<BillDetail> getBillDetailsByBillId(Long billId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q=s.createQuery("FROM BillDetail bd WHERE bd.bill.billId = :billId", BillDetail.class);
        q.setParameter("billId", billId);
        return q.getResultList();
    }
}
