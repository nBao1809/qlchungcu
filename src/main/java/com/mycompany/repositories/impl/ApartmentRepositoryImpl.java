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

import com.mycompany.pojo.Apartment;
import com.mycompany.pojo.User;
import com.mycompany.repositories.ApartmentRepository;

@Repository
@Transactional
public class ApartmentRepositoryImpl implements ApartmentRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Apartment> getAllApartments() {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query<Apartment> q = s.createNamedQuery("Apartment.findAll", Apartment.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Apartment addApartment(Apartment a) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.persist(a);
            return a;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void updateApartment(Apartment a) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.update(a);
        } catch (Exception e) {
            // Có thể log lỗi ở đây nếu cần
        }
    }

    @Override
    public Apartment getApartmentById(Long apartmentId) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query<Apartment> q = s.createNamedQuery("Apartment.findByApartmentId", Apartment.class);
            q.setParameter("apartmentId", apartmentId);
            return q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<User> getResidentsOfApartment(Long apartmentId) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query q = s.createQuery(
                "SELECT u FROM User u JOIN u.apartments a WHERE a.id = :apartmentId", User.class);
            q.setParameter("apartmentId", apartmentId);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}