/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.mycompany.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.pojo.Bill;
import com.mycompany.pojo.FeeType;
import com.mycompany.pojo.User;

/**
 *
 * @author baoto
 */
@Service
public interface BillService {
    List<Bill> getAllBills();
    Bill createBill(Map<String, String> params);
    Bill getBillById(Long billId);
    boolean confirmPayment(Long billId, String status);
    List<Bill> getBillsByUser(User user);
    Bill getUserBillById(User user, Long billId);
    Bill uploadPaymentProof(User user, Long billId, MultipartFile file);
    Bill createOnlinePayment(User user, Long billId, Map<String, String> params);
    public List<FeeType> getAllFeeTypes();
}
