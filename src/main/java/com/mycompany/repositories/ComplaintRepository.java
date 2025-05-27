/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.repositories;

import java.util.List;

import com.mycompany.pojo.Complaint;
import com.mycompany.pojo.User;

/**
 *
 * @author baoto
 */
public interface ComplaintRepository {
    List<Complaint> getAllComplaints();
    Complaint getComplaintById(Long id);
    List<Complaint> getComplaintsByUser(User user);
    Complaint addComplaint(Complaint complaint);
    void updateComplaint(Complaint complaint);
}
