/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;
import java.util.List;
import java.util.Map;

import com.mycompany.pojo.Apartment;
import com.mycompany.pojo.User;

public interface ApartmentService {
    List<Apartment> getAllApartments();
    Apartment addApartment(Map<String, String> params);
    Apartment updateApartmentInfo(Long apartmentId, Map<String, String> params);
    Apartment getApartmentById(Long apartmentId);
    List<User> getResidentsOfApartment(Long apartmentId);
}