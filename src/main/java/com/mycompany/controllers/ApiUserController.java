/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.pojo.User;
import com.mycompany.services.UserService;
import com.mycompany.utils.JwtUtils;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {

    @Autowired
    private UserService userDetailsService;


    // @PostMapping(path = "/users",
    //         consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
    //         produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<User> create(@RequestParam Map<String, String> params, @RequestParam(value = "avatar") MultipartFile avatar) {
    //     return new ResponseEntity<>(this.userDetailsService.addUser(params, avatar), HttpStatus.CREATED);
    // }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {

        if (this.userDetailsService.authenticate(username, password)) {
            try {
                String role = userDetailsService.getUserByUsername(username).getRole();
                String token = JwtUtils.generateToken(username, role);
                return ResponseEntity.ok().body(Collections.singletonMap("token", token));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Lỗi khi tạo JWT");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai thông tin đăng nhập");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(Principal principal, @RequestBody Map<String, String> body) {
        String newPassword = body.get("newPassword");
        User user = userDetailsService.getUserByUsername(principal.getName());
        if (user != null) {
            userDetailsService.changePassword(user, newPassword);
            return ResponseEntity.ok("Đổi mật khẩu thành công");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi đổi mật khẩu: Không tìm thấy người dùng");
    }
    // Cập nhật avatar cá nhân

    @PostMapping(path = "/update-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAvatar(Principal principal, @RequestParam("avatar") MultipartFile avatar) {
        User user = userDetailsService.getUserByUsername(principal.getName());
        if (user != null) {
            userDetailsService.updateAvatar(user, avatar);
            return ResponseEntity.ok("Cập nhật avatar thành công");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy người dùng");

    }

    @RequestMapping("/auth/profile")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<User> getProfile(Principal principal) {
        return new ResponseEntity<>(this.userDetailsService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }
    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(value = "role", required = false) String role) {
        List<User> users = userDetailsService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    // Đăng ký user do admin thực hiện (tạo user cư dân hoặc admin)
    @PostMapping(path = "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createAdminUser(@RequestBody Map<String, String> params) {
        return new ResponseEntity<>(this.userDetailsService.addUser(params, null), HttpStatus.CREATED);
    }

    // Cập nhật thông tin người dùng
    @PutMapping("/admin/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") Long userId, @RequestBody Map<String, String> params) {
        User updated = userDetailsService.updateUserInfo(userId, params);
        if (updated != null)
            return ResponseEntity.ok(updated);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Khóa hoặc mở khóa tài khoản
    @PutMapping("/admin/users/{userId}/toggle-status")
    public ResponseEntity<?> toggleStatus(@PathVariable("userId") Long userId) {
        boolean result = userDetailsService.toggleUserStatus(userId);
        if (result)
            return ResponseEntity.ok("Cập nhật trạng thái thành công");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
    }

    // GET /api/users/bills - Danh sách hóa đơn của mình
    // @GetMapping("/users/bills")
    // public ResponseEntity<List<Bill>> getUserBills(Principal principal) {
    //     User user = userDetailsService.getUserByUsername(principal.getName());
    //     return ResponseEntity.ok(billService.getBillsByUser(user));
    // }

    // GET /api/users/bills/{billId} - Chi tiết hóa đơn của mình
    // @GetMapping("/users/bills/{billId}")
    // public ResponseEntity<Bill> getUserBillDetail(Principal principal, @PathVariable("billId") Long billId) {
    //     User user = userDetailsService.getUserByUsername(principal.getName());
    //     Bill bill = billService.getUserBillById(user, billId);
    //     if (bill != null) {
    //         return ResponseEntity.ok(bill);
    //     }
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    // }

    // POST /api/users/bills/{billId}/payment-proof - Upload minh chứng chuyển khoản
    // @PostMapping(path = "/users/bills/{billId}/payment-proof", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // public ResponseEntity<Bill> uploadPaymentProof(Principal principal, @PathVariable("billId") Long billId, @RequestParam("file") MultipartFile file) {
    //     User user = userDetailsService.getUserByUsername(principal.getName());
    //     Bill bill = billService.uploadPaymentProof(user, billId, file);
    //     if (bill != null) {
    //         return ResponseEntity.ok(bill);
    //     }
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    // }

    // POST /api/users/bills/{billId}/online-payment - Tạo giao dịch thanh toán online
    // @PostMapping("/users/bills/{billId}/online-payment")
    // public ResponseEntity<Bill> createOnlinePayment(Principal principal, @PathVariable("billId") Long billId, @RequestBody Map<String, String> params) {
    //     User user = userDetailsService.getUserByUsername(principal.getName());
    //     Bill bill = billService.createOnlinePayment(user, billId, params);
    //     if (bill != null) {
    //         return ResponseEntity.ok(bill);
    //     }
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    // }
}
