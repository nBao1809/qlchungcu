/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.mycompany.dto.CreateBillDTO;
import com.mycompany.pojo.Apartment;
import com.mycompany.pojo.Bill;
import com.mycompany.pojo.BillDetail;
import com.mycompany.pojo.FeeType;
import com.mycompany.pojo.User;
import com.mycompany.repositories.BillDetailRepository;
import com.mycompany.services.ApartmentService;
import com.mycompany.services.BillService;

@RestController
@RequestMapping("/api")
public class ApiBillController {
    private static final Logger logger = Logger.getLogger(ApiBillController.class.getName());
    
    @Autowired
    private BillService billService;
    
    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private BillDetailRepository billDetailRepo;

    // GET /api/admin/bills - Danh sách hóa đơn toàn bộ căn hộ
    @GetMapping("/admin/bills")
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    // POST /api/admin/bills - Tạo hóa đơn mới cho căn hộ
    @PostMapping(path = "/admin/bills", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBill(@RequestBody CreateBillDTO billDTO) {
        logger.log(Level.INFO, "Bắt đầu tạo hóa đơn mới cho căn hộ {0}", billDTO.getApartmentId());
        
        // Validate required fields
        if (billDTO.getApartmentId() == null || billDTO.getBillDetails() == null || billDTO.getBillDetails().isEmpty()) {
            logger.log(Level.WARNING, "Thiếu thông tin bắt buộc khi tạo hóa đơn: apartmentId={0}, billDetails={1}", 
                new Object[]{billDTO.getApartmentId(), billDTO.getBillDetails()});
            return ResponseEntity.badRequest().body("Missing required information");
        }

        // Validate month and year
        if (billDTO.getMonth() <= 0 || billDTO.getMonth() > 12 || billDTO.getYear() < 2020) {
            logger.log(Level.WARNING, "Tháng/năm không hợp lệ: month={0}, year={1}", 
                new Object[]{billDTO.getMonth(), billDTO.getYear()});
            return ResponseEntity.badRequest().body("Invalid month/year");
        }

        // Validate details
        for (com.mycompany.dto.BillDetailDTO detail : billDTO.getBillDetails()) {
            if (detail.getFeeTypeId() == null || detail.getQuantity() == null || detail.getUnitPrice() == null) {
                logger.log(Level.WARNING, "Thiếu thông tin chi tiết hóa đơn: {0}", detail);
                return ResponseEntity.badRequest().body("Each detail must have: fee_type_id, quantity, unit_price");
            }

            // Validate positive values
            if (detail.getQuantity().compareTo(BigDecimal.ZERO) <= 0 || 
                detail.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                logger.log(Level.WARNING, "Số lượng hoặc đơn giá không hợp lệ: quantity={0}, unitPrice={1}", 
                    new Object[]{detail.getQuantity(), detail.getUnitPrice()});
                return ResponseEntity.badRequest().body("Quantity and unit price must be positive");
            }
        }

        try {
            // Validate apartment exists
            Apartment apartment = apartmentService.getApartmentById(billDTO.getApartmentId());
            if (apartment == null) {
                logger.log(Level.WARNING, "Không tìm thấy căn hộ: {0}", billDTO.getApartmentId());
                return ResponseEntity.badRequest().body("Apartment not found");
            }

            Bill newBill = billService.createBill(billDTO);
            if (newBill != null) {
                return ResponseEntity.ok(newBill);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating bill");
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi tạo hóa đơn mới", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("Error creating bill: " + e.getMessage());
        }
    }

    // GET /api/admin/bills/{billId} - Chi tiết hóa đơn
    @GetMapping("/admin/bills/{billId}")
    public ResponseEntity<Bill> getBillDetail(@PathVariable Long billId) {
        Bill bill = billService.getBillById(billId);
        if (bill != null) {
            return ResponseEntity.ok(bill);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // GET /api/admin/bills/{billId}/details - Lấy chi tiết hóa đơn kèm các khoản phí
    @GetMapping("/admin/bills/{billId}/details")
    public ResponseEntity<?> getBillWithDetails(@PathVariable Long billId) {
        Bill bill = billService.getBillById(billId);
        if (bill == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy hóa đơn");
        }
        List<BillDetail> details = billDetailRepo.getBillDetailsByBillId(billId);
        return ResponseEntity.ok(Map.of(
            "bill", bill,
            "details", details
        ));
    }

    // PUT /api/admin/bills/{billId}/confirm-payment - Xác nhận hoặc từ chối thanh toán
    @PutMapping("/admin/bills/{billId}/confirm-payment")
    public ResponseEntity<?> confirmPayment(@PathVariable Long billId, @RequestBody Map<String, String> params) {
        boolean result = billService.confirmPayment(billId, params.get("status")); // status: CONFIRMED/REJECTED
        if (result) {
            logger.log(Level.INFO, "Xác nhận thanh toán thành công: billId={0}, status={1}", 
                new Object[]{billId, params.get("status")});
            return ResponseEntity.ok("Cập nhật trạng thái thanh toán thành công");
        }
        logger.log(Level.WARNING, "Không tìm thấy hóa đơn {0} để xác nhận thanh toán", billId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy hóa đơn");
    }

    // GET /api/admin/bills/fee-types - Lấy danh sách loại phí
    @GetMapping("/admin/bills/fee-types")
    public ResponseEntity<List<FeeType>> getFeeTypes() {
        List<FeeType> feeTypes = billService.getAllFeeTypes();
        return ResponseEntity.ok(feeTypes);
    }

    // GET /api/users/bills - Danh sách hóa đơn của mình
    @GetMapping("/users/bills")
    public ResponseEntity<List<Bill>> getUserBills(@RequestAttribute("user") User user) {
        List<Bill> bills = billService.getBillsByUser(user);
        return ResponseEntity.ok(bills);
    }

    // GET /api/users/bills/{billId} - Chi tiết hóa đơn của mình
    @GetMapping("/users/bills/{billId}")
    public ResponseEntity<Bill> getUserBillDetail(@RequestAttribute("user") User user, @PathVariable Long billId) {
        Bill bill = billService.getUserBillById(user, billId);
        if (bill != null) {
            return ResponseEntity.ok(bill);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // POST /api/users/bills/{billId}/payment-proof - Upload minh chứng chuyển khoản
    @PostMapping(path = "/users/bills/{billId}/payment-proof", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Bill> uploadPaymentProof(@RequestAttribute("user") User user,
                                                  @PathVariable Long billId,
                                                  @RequestParam("file") MultipartFile file) {
        Bill bill = billService.uploadPaymentProof(user, billId, file);
        if (bill != null) {
            logger.log(Level.INFO, "Upload minh chứng thanh toán thành công: billId={0}, user={1}", 
                new Object[]{billId, user.getUsername()});
            return ResponseEntity.ok(bill);
        }
        logger.log(Level.WARNING, "Không thể upload minh chứng thanh toán: billId={0}, user={1}", 
            new Object[]{billId, user.getUsername()});
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // POST /api/users/bills/{billId}/online-payment - Tạo giao dịch thanh toán online
    @PostMapping("/users/bills/{billId}/online-payment")
    public ResponseEntity<Bill> createOnlinePayment(@RequestAttribute("user") User user,
                                                   @PathVariable Long billId,
                                                   @RequestBody Map<String, String> params) {
        Bill bill = billService.createOnlinePayment(user, billId, params);
        if (bill != null) {
            logger.log(Level.INFO, "Tạo giao dịch thanh toán online thành công: billId={0}, user={1}, method={2}", 
                new Object[]{billId, user.getUsername(), params.get("paymentMethod")});
            return ResponseEntity.ok(bill);
        }
        logger.log(Level.WARNING, "Không thể tạo giao dịch thanh toán online: billId={0}, user={1}", 
            new Object[]{billId, user.getUsername()});
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
