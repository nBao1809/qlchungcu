/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.mycompany.services;

import java.util.List;
import java.util.Map;

import com.mycompany.pojo.User;
import com.mycompany.pojo.Vehicle;

/**
 *
 * @author baoto
 */
public interface VehicleService {
List<Vehicle> getAllVehicles();
List<Vehicle> getVehiclesOfUserAndRelatives(User user);
Vehicle addVehicle(Map<String, String> params);
Vehicle updateVehicle(Long vehicleId, Map<String, String> params);
}
