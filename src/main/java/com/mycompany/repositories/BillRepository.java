/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.mycompany.repositories;

import java.util.List;

import com.mycompany.pojo.Bill;
import com.mycompany.pojo.User;

/**
 *
 * @author baoto
 */
public interface BillRepository {
    List<Bill> getAllBills();
    Bill addBill(Bill bill);
    List<Bill> getBillsByResident(User user);
    Bill getBillByIdAndResident(Long billId, User user);
    Bill getBillById(Long billId);
    void updateBill(Bill bill);
    Bill findByApartmentAndMonthAndYear(Long apartmentId, short month, int year);
}
