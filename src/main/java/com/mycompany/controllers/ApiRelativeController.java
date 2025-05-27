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
import org.springframework.web.server.ResponseStatusException;

import com.mycompany.pojo.Relative;
import com.mycompany.pojo.User;
import com.mycompany.services.RelativeService;
import com.mycompany.services.UserService;

/**
 *
 * @author baoto
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiRelativeController {

    @Autowired
    private RelativeService relativeService;

    @Autowired
    private UserService userService;

    // (Admin) Xem danh sách toàn bộ người thân
    @GetMapping("/admin/relatives")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Relative>> getAllRelatives() {
        try {
            return ResponseEntity.ok(relativeService.getAllRelatives());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving relatives", e);
        }
    }

    // (Cư dân) Xem danh sách người thân của mình
    @GetMapping("/users/relatives")
    @PreAuthorize("hasAuthority('RESIDENT')")
    public ResponseEntity<List<Relative>> getMyRelatives(Principal principal) {
        try {
            User user = userService.getUserByUsername(principal.getName());
            return ResponseEntity.ok(relativeService.getRelativesByResident(user));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving relatives", e);
        }
    }

    // Thêm người thân mới
    @PostMapping("/users/relatives") 
    @PreAuthorize("hasAuthority('RESIDENT')")
    public ResponseEntity<Relative> addRelative(Principal principal, @RequestBody Map<String, String> params) {
        try {
            User user = userService.getUserByUsername(principal.getName());
            Relative relative = relativeService.addRelative(user, params);
            return new ResponseEntity<>(relative, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating relative", e);
        }
    }

    // Cập nhật thông tin người thân
    @PutMapping("/users/relatives/{relativeId}")
    @PreAuthorize("hasAuthority('RESIDENT')")
    public ResponseEntity<Relative> updateRelative(Principal principal, @PathVariable("relativeId") Long relativeId, @RequestBody Map<String, String> params) {
        try {
            User user = userService.getUserByUsername(principal.getName());
            Relative existing = relativeService.getRelativeById(relativeId);
            if (existing == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Relative not found");
            }
            if (!existing.getResidentId().getId().equals(user.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
            }
            
            Relative updated = relativeService.updateRelative(relativeId, params);
            return ResponseEntity.ok(updated);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating relative", e);
        }
    }
}
