/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.pojo.Complaint;
import com.mycompany.pojo.User;
import com.mycompany.services.ComplaintService;
import com.mycompany.services.UserService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author baoto
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiComplaintController {
    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private UserService userService;

    // GET /api/admin/complaints
    @GetMapping("/admin/complaints")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    // PUT /api/admin/complaints/{id}
    @PutMapping("/admin/complaints/{id}")
    public ResponseEntity<?> updateComplaintStatus(@PathVariable("id") Long id, @RequestBody Map<String, String> params, Principal principal) {
        User admin = userService.getUserByUsername(principal.getName());
        complaintService.updateComplaintStatus(id, params.get("status"), admin);
        return ResponseEntity.ok("Cập nhật trạng thái thành công");
    }

    // GET /api/complaints/{id}
    @GetMapping("/complaints/{id}")
    public ResponseEntity<Complaint> getComplaintDetail(@PathVariable("id") Long id) {
        Complaint complaint = complaintService.getComplaintById(id);
        if (complaint != null) {
            return ResponseEntity.ok(complaint);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // GET /api/users/complaints
    @GetMapping("/users/complaints")
    public ResponseEntity<List<Complaint>> getComplaintsByUser(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(complaintService.getComplaintsByUser(user));
    }

    // POST /api/users/complaints
    @PostMapping(path = "/users/complaints", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Complaint> addComplaint(Principal principal, @RequestParam Map<String, String> params, @RequestParam(value = "imageProof", required = false) MultipartFile imageProof) {
        User user = userService.getUserByUsername(principal.getName());
        Complaint complaint = complaintService.addComplaint(params, user, imageProof);
        return new ResponseEntity<>(complaint, HttpStatus.CREATED);
    }
}
