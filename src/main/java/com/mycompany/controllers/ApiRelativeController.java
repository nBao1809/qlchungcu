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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mycompany.pojo.Relative;
import com.mycompany.pojo.User;
import com.mycompany.services.RelativeService;
import com.mycompany.services.UserService;

/**
 *
 * @author baoto
 */
public class ApiRelativeController {

    @Autowired
    private RelativeService relativeService;

    @Autowired
    private UserService userService;

    // (Admin) Xem danh sách toàn bộ người thân
    @GetMapping("/admin/relatives")
    public ResponseEntity<List<Relative>> getAllRelatives() {
        return ResponseEntity.ok(relativeService.getAllRelatives());
    }

    // (Cư dân) Xem danh sách người thân của mình
    @GetMapping("/users/relatives")
    public ResponseEntity<List<Relative>> getMyRelatives(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(relativeService.getRelativesByResident(user));
    }

    // Thêm người thân mới
    @PostMapping("/users/relatives")
    public ResponseEntity<Relative> addRelative(Principal principal, @RequestBody Map<String, String> params) {
        User user = userService.getUserByUsername(principal.getName());
        Relative relative = relativeService.addRelative(user, params);
        return new ResponseEntity<>(relative, HttpStatus.CREATED);
    }

    // Cập nhật thông tin người thân
    @PutMapping("/users/relatives/{relativeId}")
    public ResponseEntity<Relative> updateRelative(@PathVariable("relativeId") Long relativeId, @RequestBody Map<String, String> params) {
        Relative updated = relativeService.updateRelative(relativeId, params);
        if (updated != null)
            return ResponseEntity.ok(updated);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
