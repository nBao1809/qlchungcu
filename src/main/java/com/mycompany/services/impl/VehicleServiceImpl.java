/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.services.impl;
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

    
        if (params.containsKey("relativeId")) {
            Relative relative = relativeRepo.getRelativeById(Long.parseLong(params.get("relativeId")));
            v.setRelativeId(relative);
            v.setResidentId(null);
        } else if (params.containsKey("residentId")) {
            User resident = userRepo.getUserById(Long.parseLong(params.get("residentId")));
            v.setResidentId(resident);
            v.setRelativeId(null);
        }

        if (params.containsKey("status")) {
            v.setStatus(Boolean.valueOf(params.get("status")));
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
        // ... các trường khác nếu có ...
        vehicleRepo.updateVehicle(v);
        return v;
    }
}
