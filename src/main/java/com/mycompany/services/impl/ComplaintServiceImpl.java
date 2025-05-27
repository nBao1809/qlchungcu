/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mycompany.pojo.Complaint;
import com.mycompany.pojo.User;
import com.mycompany.repositories.ComplaintRepository;
import com.mycompany.services.ComplaintService;

/**
 *
 * @author baoto
 */
@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private ComplaintRepository complaintRepo;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Complaint> getAllComplaints() {
        return complaintRepo.getAllComplaints();
    }

    @Override
    public Complaint getComplaintById(Long id) {
        return complaintRepo.getComplaintById(id);
    }

    @Override
    public List<Complaint> getComplaintsByUser(User user) {
        return complaintRepo.getComplaintsByUser(user);
    }

    @Override
    public Complaint addComplaint(Map<String, String> params, User user, MultipartFile imageProof) {
        Complaint complaint = new Complaint();        complaint.setTitle(params.get("title"));
        complaint.setContent(params.get("description"));
        Date now = new Date();
        complaint.setSubmittedDate(now);
        complaint.setCreatedAt(now);
        complaint.setUpdatedAt(now);
        complaint.setStatus("PENDING");
        complaint.setResidentId(user);
        if (imageProof != null && !imageProof.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(imageProof.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                complaint.setImageProof(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ComplaintServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return complaintRepo.addComplaint(complaint);
    }

    @Override
    public void updateComplaintStatus(Long id, String status, User admin) {
        Complaint complaint = complaintRepo.getComplaintById(id);
        if (complaint != null) {
            complaint.setStatus(status);
            complaint.setResolvedByAdminId(admin);
            complaint.setResolvedDate(new Date());
            complaintRepo.updateComplaint(complaint);
        }
    }
}
