/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.pojo.User;
import com.mycompany.pojo.Vehicle;
import com.mycompany.services.UserService;
import com.mycompany.services.VehicleService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiVehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserService userService;

    // Admin APIs
    @GetMapping("/admin/vehicles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    // User APIs
    @GetMapping("/users/vehicles")
    @PreAuthorize("hasAuthority('RESIDENT')")
    public ResponseEntity<List<Vehicle>> getUserVehicles(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<Vehicle> vehicles = vehicleService.getVehiclesOfUserAndRelatives(user);
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping("/users/vehicles")
    @PreAuthorize("hasAuthority('RESIDENT')")
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Map<String, String> params, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        params.put("residentId", user.getId().toString());
        
        Vehicle vehicle = vehicleService.addVehicle(params);
        if (vehicle != null)
            return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/admin/vehicles/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable("id") Long vehicleId, @RequestBody Map<String, String> params) {
        Vehicle updated = vehicleService.updateVehicle(vehicleId, params);
        if (updated != null)
            return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    // API for managing relative's vehicles
    @PostMapping("/users/relatives/{relativeId}/vehicles")
    @PreAuthorize("hasAuthority('RESIDENT')")
    public ResponseEntity<Vehicle> addVehicleForRelative(
            @PathVariable("relativeId") Long relativeId,
            @RequestBody Map<String, String> params,
            Principal principal) {
        params.put("relativeId", relativeId.toString());
        Vehicle v = vehicleService.addVehicle(params);
        if (v != null)
            return new ResponseEntity<>(v, HttpStatus.CREATED);
        return ResponseEntity.badRequest().build();
    }
}
