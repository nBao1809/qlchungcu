/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repositories.impl;
/**
 *
 * @author baoto
 */
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.pojo.Relative;
import com.mycompany.pojo.User;
import com.mycompany.repositories.RelativeRepository;
@Repository
@Transactional
public class RelativeRepositoryImpl implements RelativeRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Relative> getAllRelatives() {
        Session s = this.factory.getObject().getCurrentSession();
        Query<Relative> q = s.createNamedQuery("Relative.findAll", Relative.class);
        return q.getResultList();
    }

    @Override
    public List<Relative> getRelativesByResident(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<Relative> q = s.createNamedQuery("Relative.findByResidentId", Relative.class);
        q.setParameter("residentId", user.getId());
        return q.getResultList();
    }

    @Override
    public Relative addRelative(Relative r) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(r);
        return r;
    }

    @Override
    public void updateRelative(Relative r) {
        Session s = this.factory.getObject().getCurrentSession();
        s.update(r);
    }

    @Override
    public Relative getRelativeById(Long id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<Relative> q = s.createNamedQuery("Relative.findByRelativeId", Relative.class);
        q.setParameter("relativeId", id);
        return q.getSingleResult();
    }
}
