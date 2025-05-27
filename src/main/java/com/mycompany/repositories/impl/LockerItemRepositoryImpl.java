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

import com.mycompany.pojo.LockerItem;
import com.mycompany.pojo.User;
import com.mycompany.repositories.LockerItemRepository;

/**
 *
 * @author baoto
 */
@Repository
@Transactional
public class LockerItemRepositoryImpl implements LockerItemRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<LockerItem> getAllLockerItems() {
        Session s = this.factory.getObject().getCurrentSession();
        Query<LockerItem> q = s.createNamedQuery("LockerItem.findAll", LockerItem.class);
        return q.getResultList();
    }

    @Override
    public LockerItem addLockerItem(LockerItem item) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(item);
        return item;
    }

    @Override
    public void updateLockerItem(LockerItem item) {
        Session s = this.factory.getObject().getCurrentSession();
        s.update(item);
    }

    @Override
    public LockerItem getLockerItemById(Long itemId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<LockerItem> q = s.createNamedQuery("LockerItem.findByItemId", LockerItem.class);
        q.setParameter("itemId", itemId);
        return q.getSingleResult();
    }

    @Override
    public List<LockerItem> getLockerItemsByResident(User resident) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<LockerItem> q = s.createQuery("FROM LockerItem l WHERE l.residentId = :resident", LockerItem.class);
        q.setParameter("resident", resident);
        return q.getResultList();
    }
}
