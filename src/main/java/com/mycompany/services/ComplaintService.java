/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.services;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.mycompany.pojo.Complaint;
import com.mycompany.pojo.User;

/**
 *
 * @author baoto
 */
public interface ComplaintService {
    List<Complaint> getAllComplaints();
    Complaint getComplaintById(Long id);
    List<Complaint> getComplaintsByUser(User user);
    Complaint addComplaint(Map<String, String> params, User user, MultipartFile imageProof);
    void updateComplaintStatus(Long id, String status, User admin);
}
