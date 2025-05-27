/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.repositories;

import java.util.List;

import com.mycompany.pojo.User;
import com.mycompany.pojo.Vehicle;

/**
 *
 * @author baoto
 */
public interface VehicleRepository {

    List<Vehicle> getAllVehicles();

    List<Vehicle> getVehiclesOfUserAndRelatives(User user);

    Vehicle addVehicle(Vehicle v);

    void updateVehicle(Vehicle v);

    Vehicle getVehicleById(Long id);
}
