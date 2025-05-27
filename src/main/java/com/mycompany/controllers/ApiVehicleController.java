/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.controllers;

import com.mycompany.pojo.User;
import com.mycompany.pojo.Vehicle;
import com.mycompany.services.UserService;
import com.mycompany.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiVehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserService userService;

    // (Admin) Xem toàn bộ phương tiện
    @GetMapping("/admin/vehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    // (Cư dân) Xem phương tiện của mình và người thân
    @GetMapping("/users/vehicles")
    public ResponseEntity<List<Vehicle>> getMyVehicles(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(vehicleService.getVehiclesOfUserAndRelatives(user));
    }

    // Thêm phương tiện mới
    @PostMapping("/users/vehicles")
    public ResponseEntity<Vehicle> addVehicle(Principal principal, @RequestBody Map<String, String> params) {
        User user = userService.getUserByUsername(principal.getName());
        Vehicle vehicle = vehicleService.addVehicle(params);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    // Cập nhật thông tin phương tiện
    @PutMapping("/users/vehicles/{vehicleId}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable("vehicleId") Long vehicleId, @RequestBody Map<String, String> params) {
        Vehicle updated = vehicleService.updateVehicle(vehicleId, params);
        if (updated != null)
            return ResponseEntity.ok(updated);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
