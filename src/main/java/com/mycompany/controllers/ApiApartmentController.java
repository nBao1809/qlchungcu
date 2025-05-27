package com.mycompany.controllers;

import com.mycompany.pojo.Apartment;
import com.mycompany.pojo.User;
import com.mycompany.services.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/apartments")
@CrossOrigin
public class ApiApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    // Lấy danh sách tất cả căn hộ
    @GetMapping
    public ResponseEntity<List<Apartment>> getApartments() {
        List<Apartment> apartments = apartmentService.getAllApartments();
        return ResponseEntity.ok(apartments);
    }

    // Thêm căn hộ mới
    @PostMapping
    public ResponseEntity<Apartment> createApartment(@RequestBody Map<String, String> params) {
        Apartment apartment = apartmentService.addApartment(params);
        return new ResponseEntity<>(apartment, HttpStatus.CREATED);
    }

    // Cập nhật thông tin căn hộ
    @PutMapping("/{apartmentId}")
    public ResponseEntity<Apartment> updateApartment(@PathVariable("apartmentId") Long apartmentId,
                                                     @RequestBody Map<String, String> params) {
        Apartment updated = apartmentService.updateApartmentInfo(apartmentId, params);
        if (updated != null)
            return ResponseEntity.ok(updated);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Xem chi tiết căn hộ và danh sách cư dân
    @GetMapping("/{apartmentId}")
    public ResponseEntity<?> getApartmentDetail(@PathVariable("apartmentId") Long apartmentId) {
        Apartment apartment = apartmentService.getApartmentById(apartmentId);
        if (apartment == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy căn hộ");
        List<User> residents = apartmentService.getResidentsOfApartment(apartmentId);
        return ResponseEntity.ok(Map.of(
            "apartment", apartment,
            "residents", residents
        ));
    }
}