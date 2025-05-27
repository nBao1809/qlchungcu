/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repositories.impl;

import java.util.ArrayList;
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
        try {
            Session s = this.factory.getObject().getCurrentSession();
            String hql = "FROM Vehicle v WHERE v.status = true ORDER BY v.createdAt DESC";
            Query<Vehicle> q = s.createQuery(hql, Vehicle.class);
            List<Vehicle> vehicles = q.getResultList();
            return vehicles != null ? vehicles : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Vehicle> getVehiclesOfUserAndRelatives(User user) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            // Lấy phương tiện của cư dân và người thân của họ
            String hql = "FROM Vehicle v " +
                        "LEFT JOIN v.relativeId r " +
                        "WHERE (v.residentId.id = :userId " +
                        "OR (r IS NOT NULL AND r.residentId.id = :userId)) " +
                        "AND v.status = true " +
                        "ORDER BY v.createdAt DESC";
            Query<Vehicle> q = s.createQuery(hql, Vehicle.class);
            q.setParameter("userId", user.getId());
            List<Vehicle> vehicles = q.getResultList();
            
            if (vehicles.isEmpty()) {
                System.out.println("No vehicles found for user: " + user.getId());
            }
            
            return vehicles != null ? vehicles : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error getting vehicles for user: " + user.getId());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Vehicle addVehicle(Vehicle v) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.persist(v);
            return v;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void updateVehicle(Vehicle v) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.update(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Vehicle getVehicleById(Long id) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query<Vehicle> q = s.createNamedQuery("Vehicle.findByVehicleId", Vehicle.class);
            q.setParameter("vehicleId", id);
            return q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
