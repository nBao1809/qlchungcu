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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.pojo.LockerItem;
import com.mycompany.pojo.User;
import com.mycompany.services.LockerItemService;
import com.mycompany.services.UserService;

/**
 *
 * @author baoto
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiLockerItemController {
    @Autowired
    private LockerItemService lockerItemService;
    @Autowired
    private UserService userService;

    // GET /api/admin/locker-items
    @GetMapping("/admin/locker-items")
    public ResponseEntity<List<LockerItem>> getAllLockerItems() {
        return ResponseEntity.ok(lockerItemService.getAllLockerItems());
    }

    // POST /api/admin/locker-items
    @PostMapping("/admin/locker-items")
    public ResponseEntity<LockerItem> addLockerItem(@RequestBody Map<String, String> params, Principal principal) {
        User admin = userService.getUserByUsername(principal.getName());
        LockerItem item = lockerItemService.addLockerItem(params, admin);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    // PUT /api/admin/locker-items/{itemId}
    @PutMapping("/admin/locker-items/{itemId}")
    public ResponseEntity<?> updateLockerItemStatus(@PathVariable("itemId") Long itemId, @RequestBody Map<String, String> params, Principal principal) {
        User admin = userService.getUserByUsername(principal.getName());
        lockerItemService.updateLockerItemStatus(itemId, params.get("status"), admin);
        return ResponseEntity.ok("Cập nhật trạng thái thành công");
    }

    // GET /api/users/locker-items
    @GetMapping("/users/locker-items")
    public ResponseEntity<List<LockerItem>> getLockerItemsByResident(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(lockerItemService.getLockerItemsByResident(user));
    }
}
