/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.pojo.Bill;
import com.mycompany.pojo.BillDetail;
import com.mycompany.pojo.FeeType;
import com.mycompany.pojo.User;
import com.mycompany.repositories.BillDetailRepository;
import com.mycompany.services.BillService;

/**
 *
 * @author baoto
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiBillController {

    @Autowired
    private BillService billService;

    @Autowired
    private BillDetailRepository billDetailRepo;


    // GET /api/admin/bills - Danh sách hóa đơn toàn bộ căn hộ
    @GetMapping("/admin/bills")
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    // POST /api/admin/bills - Tạo hóa đơn mới cho căn hộ
    @PostMapping("/admin/bills")
    public ResponseEntity<Bill> createBill(@RequestBody Map<String, String> params) {
        Bill bill = billService.createBill(params);
        return new ResponseEntity<>(bill, HttpStatus.CREATED);
    }

    // GET /api/admin/bills/{billId} - Chi tiết hóa đơn
    @GetMapping("/admin/bills/{billId}")
    public ResponseEntity<Bill> getBillDetail(@PathVariable("billId") Long billId) {
        Bill bill = billService.getBillById(billId);
        if (bill != null) {
            return ResponseEntity.ok(bill);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // PUT /api/admin/bills/{billId}/confirm-payment - Xác nhận hoặc từ chối thanh toán
    @PutMapping("/admin/bills/{billId}/confirm-payment")
    public ResponseEntity<?> confirmPayment(@PathVariable("billId") Long billId, @RequestBody Map<String, String> params) {
        boolean result = billService.confirmPayment(billId, params.get("status")); // status: CONFIRMED/REJECTED
        if (result) {
            return ResponseEntity.ok("Cập nhật trạng thái thanh toán thành công");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy hóa đơn");
    }

    // GET /api/admin/bills/fee-types - Lấy danh sách loại phí
    @GetMapping("/admin/bills/fee-types")
    public ResponseEntity<List<FeeType>> getFeeTypes() {
        List<FeeType> feeTypes = billService.getAllFeeTypes();
        return ResponseEntity.ok(feeTypes);
    }

    // GET /api/users/bills - Danh sách hóa đơn của mình (chuẩn RESTful, không trùng)
    @GetMapping("/users/bills")
    public ResponseEntity<List<Bill>> getUserBills(@RequestAttribute("user") User user) {
        List<Bill> bills = billService.getBillsByUser(user);
        return ResponseEntity.ok(bills);
    }

    // GET /api/users/bills/{billId} - Chi tiết hóa đơn của mình (chuẩn RESTful, không trùng)
    @GetMapping("/users/bills/{billId}")
    public ResponseEntity<Bill> getUserBillDetail(@RequestAttribute("user") User user, @PathVariable("billId") Long billId) {
        Bill bill = billService.getUserBillById(user, billId);
        if (bill != null)
            return ResponseEntity.ok(bill);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // POST /api/users/bills/{billId}/payment-proof - Upload minh chứng chuyển khoản (chuẩn RESTful, không trùng)
    @PostMapping(path = "/users/bills/{billId}/payment-proof", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Bill> uploadPaymentProof(@RequestAttribute("user") User user,
                                                  @PathVariable("billId") Long billId,
                                                  @RequestParam("file") MultipartFile file) {
        Bill bill = billService.uploadPaymentProof(user, billId, file);
        if (bill != null)
            return ResponseEntity.ok(bill);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // POST /api/users/bills/{billId}/online-payment - Tạo giao dịch thanh toán online (chuẩn RESTful, không trùng)
    @PostMapping("/users/bills/{billId}/online-payment")
    public ResponseEntity<Bill> createOnlinePayment(@RequestAttribute("user") User user,
                                                   @PathVariable("billId") Long billId,
                                                   @RequestBody Map<String, String> params) {
        Bill bill = billService.createOnlinePayment(user, billId, params);
        if (bill != null)
            return ResponseEntity.ok(bill);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // GET /api/admin/bills/{billId}/details - Lấy chi tiết hóa đơn kèm các khoản phí
    @GetMapping("/admin/bills/{billId}/details")
    public ResponseEntity<?> getBillWithDetails(@PathVariable("billId") Long billId) {
        Bill bill = billService.getBillById(billId);
        if (bill == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy hóa đơn");
        List<BillDetail> details = billDetailRepo.getBillDetailsByBillId(billId);
        return ResponseEntity.ok(Map.of("bill", bill, "details", details));
    }
}
