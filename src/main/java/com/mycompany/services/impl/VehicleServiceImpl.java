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

import com.mycompany.pojo.Relative;
import com.mycompany.pojo.User;
import com.mycompany.pojo.Vehicle;
import com.mycompany.repositories.RelativeRepository;
import com.mycompany.repositories.UserRepository;
import com.mycompany.repositories.VehicleRepository;
import com.mycompany.services.VehicleService;

/**
 *
 * @author baoto
 */
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepo;
    @Autowired
    private RelativeRepository relativeRepo;
    @Autowired
    private UserRepository userRepo;

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepo.getAllVehicles();
    }

    @Override
    public List<Vehicle> getVehiclesOfUserAndRelatives(User user) {
        return vehicleRepo.getVehiclesOfUserAndRelatives(user);
    }

    @Override
    public Vehicle addVehicle(Map<String, String> params) {
        Vehicle v = new Vehicle();
        v.setLicensePlate(params.get("licensePlate"));
        v.setVehicleType(params.get("vehicleType"));
        
        // Set default dates
        Date now = new Date();
        v.setCreatedAt(now);
        v.setUpdatedAt(now);
        
        // Set default status to true (will be converted to 1 in database)
        v.setStatus(true);
    
        if (params.containsKey("relativeId")) {
            Relative relative = relativeRepo.getRelativeById(Long.parseLong(params.get("relativeId")));
            v.setRelativeId(relative);
            v.setResidentId(null);
        } else if (params.containsKey("residentId")) {
            User resident = userRepo.getUserById(Long.parseLong(params.get("residentId")));
            v.setResidentId(resident);
            v.setRelativeId(null);
        }

        // Allow override of default status if specified
        if (params.containsKey("status")) {
           v.setStatus("true".equalsIgnoreCase(params.get("status")));

        }

        return vehicleRepo.addVehicle(v);
    }

    @Override
    public Vehicle updateVehicle(Long vehicleId, Map<String, String> params) {
        Vehicle v = vehicleRepo.getVehicleById(vehicleId);
        if (v == null) {
            return null;
        }
        if (params.containsKey("licensePlate")) {
            v.setLicensePlate(params.get("licensePlate"));
        }
        if (params.containsKey("vehicleType")) {
            v.setVehicleType(params.get("vehicleType"));
        }
        if (params.containsKey("relativeId")) {
            if (params.get("relativeId") != null && !params.get("relativeId").isEmpty()) {
                Relative relative = relativeRepo.getRelativeById(Long.parseLong(params.get("relativeId")));
                v.setRelativeId(relative);
                v.setResidentId(null);
            } else {
                v.setRelativeId(null);
            }
        }
        if (params.containsKey("residentId")) {
            if (params.get("residentId") != null && !params.get("residentId").isEmpty()) {
                User resident = userRepo.getUserById(Long.parseLong(params.get("residentId")));
                v.setResidentId(resident);
                v.setRelativeId(null);
            } else {
                v.setResidentId(null);
            }
        }
        if (params.containsKey("status")) {
            v.setStatus("1".equals(params.get("status")));
        }
        // Update the updated_at timestamp
        v.setUpdatedAt(new Date());
        vehicleRepo.updateVehicle(v);
        return v;
    }
}
