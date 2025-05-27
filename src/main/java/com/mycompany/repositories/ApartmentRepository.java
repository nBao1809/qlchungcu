/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repositories;
import java.util.List;

import com.mycompany.pojo.Apartment;
import com.mycompany.pojo.User;

public interface ApartmentRepository {
    List<Apartment> getAllApartments();
    Apartment addApartment(Apartment a);
    void updateApartment(Apartment a);
    Apartment getApartmentById(Long apartmentId);
    List<User> getResidentsOfApartment(Long apartmentId);
}