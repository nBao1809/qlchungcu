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

import com.mycompany.pojo.User;
import com.mycompany.pojo.Vehicle;
import com.mycompany.repositories.VehicleRepository;

@Repository
@Transactional
public class VehicleRepositoryImpl implements VehicleRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Vehicle> getAllVehicles() {
        Session s = this.factory.getObject().getCurrentSession();
        Query<Vehicle> q = s.createNamedQuery("Vehicle.findAll", Vehicle.class);
        return q.getResultList();
    }

    @Override
    public List<Vehicle> getVehiclesOfUserAndRelatives(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        // Lấy phương tiện của cư dân và người thân của họ
        Query q = s.createQuery(
            "FROM Vehicle v WHERE v.residentId = :user OR v.relativeId.residentId = :user", Vehicle.class);
        q.setParameter("user", user);
        return q.getResultList();
    }

    @Override
    public Vehicle addVehicle(Vehicle v) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(v);
        return v;
    }

    @Override
    public void updateVehicle(Vehicle v) {
        Session s = this.factory.getObject().getCurrentSession();
        s.update(v);
    }

    @Override
    public Vehicle getVehicleById(Long id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<Vehicle> q = s.createNamedQuery("Vehicle.findByVehicleId", Vehicle.class);
        q.setParameter("vehicleId", id);
        return q.getSingleResult();
    }
}
