/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.pojo.Apartment;
import com.mycompany.pojo.User;
import com.mycompany.repositories.ApartmentRepository;
import com.mycompany.services.ApartmentService;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepo;

    @Override
    public List<Apartment> getAllApartments() {
        return apartmentRepo.getAllApartments();
    }

    @Override
    public Apartment addApartment(Map<String, String> params) {
        Apartment a = new Apartment();
        a.setApartmentCode(params.get("apartmentCode"));        
        a.setBlock(params.get("block"));
        if (params.get("floor") != null)
            a.setFloor(Integer.parseInt(params.get("floor")));
        a.setStatus(params.get("status"));

        // Set timestamps
        Date now = new Date();
        a.setCreatedAt(now);
        a.setUpdatedAt(now);
        
        return apartmentRepo.addApartment(a);
    }

    @Override
    public Apartment updateApartmentInfo(Long apartmentId, Map<String, String> params) {
        Apartment a = apartmentRepo.getApartmentById(apartmentId);
        if (a == null) return null;
        if (params.containsKey("apartmentCode")) a.setApartmentCode(params.get("apartmentCode"));
        if (params.containsKey("block")) a.setBlock(params.get("block"));
        if (params.containsKey("floor")) a.setFloor(Integer.parseInt(params.get("floor")));
        if (params.containsKey("status")) a.setStatus(params.get("status"));
        
        // Update timestamp
        a.setUpdatedAt(new Date());
        
        apartmentRepo.updateApartment(a);
        return a;
    }

    @Override
    public Apartment getApartmentById(Long apartmentId) {
        return apartmentRepo.getApartmentById(apartmentId);
    }

    @Override
    public List<User> getResidentsOfApartment(Long apartmentId) {
        return apartmentRepo.getResidentsOfApartment(apartmentId);
    }
}