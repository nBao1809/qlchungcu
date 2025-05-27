/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.services.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.pojo.Apartment;
import com.mycompany.pojo.Bill;
import com.mycompany.pojo.BillDetail;
import com.mycompany.pojo.FeeType;
import com.mycompany.pojo.User;
import com.mycompany.repositories.ApartmentRepository;
import com.mycompany.repositories.BillDetailRepository;
import com.mycompany.repositories.BillRepository;
import com.mycompany.repositories.FeeTypeRepository;
import com.mycompany.services.BillService;

/**
 *
 * @author baoto
 */
@Service
@Transactional
public class BillServiceImpl implements BillService {

    private static final Logger logger = Logger.getLogger(BillServiceImpl.class.getName());

    @Autowired
    private BillRepository billRepo;
    @Autowired
    private ApartmentRepository apartmentRepo;
    @Autowired 
    private Cloudinary cloudinary;
@Autowired
    private FeeTypeRepository feeTypeRepo;
    @Autowired
    private BillDetailRepository billDetailRepo;
    @Override
    public List<Bill> getAllBills() {
        return billRepo.getAllBills();
    }

    @Override
    public Bill createBill(Map<String, String> params) {
        Bill bill = new Bill();
        bill.setPaymentStatus("UNPAID");
        
        // Set timestamps
        Date now = new Date();
        bill.setCreatedAt(now);
        bill.setUpdatedAt(now);

        if (params.containsKey("month")) {
            bill.setMonth(Short.parseShort(params.get("month")));
        }
        if (params.containsKey("year")) {
            bill.setYear(Integer.parseInt(params.get("year")));
        }
        if (params.containsKey("apartment_id")) {
            Apartment apartment = apartmentRepo.getApartmentById(Long.parseLong(params.get("apartment_id")));
            bill.setApartmentId(apartment);
        }

        // Lưu bill trước để lấy billId
        bill = billRepo.addBill(bill);

        BigDecimal total = BigDecimal.ZERO;
        List<Map<String, Object>> details = new ArrayList<>();

        if (params.containsKey("bill_details")) {
            String billDetailsJson = params.get("bill_details");
            try {
                ObjectMapper mapper = new ObjectMapper();
                details = mapper.readValue(billDetailsJson, mapper.getTypeFactory().constructCollectionType(List.class, Map.class));
            } catch (IOException e) {
                Logger.getLogger(BillServiceImpl.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }

            for (Map<String, Object> d : details) {
                BillDetail detail = new BillDetail();
                FeeType feeType = feeTypeRepo.getFeeTypeById(Long.valueOf(d.get("fee_type_id").toString()));
                detail.setFeeTypeId(feeType);
                detail.setBillId(bill);
                detail.setQuantity(new BigDecimal(d.get("quantity").toString()));
                detail.setUnitPrice(new BigDecimal(d.get("unit_price").toString()));
                detail.setAmount(detail.getQuantity().multiply(detail.getUnitPrice()));
                detail.setNote((String) d.get("note"));
                billDetailRepo.addBillDetail(detail);
                total = total.add(detail.getAmount());
            }
            // Cập nhật lại tổng tiền cho bill
            bill.setTotalAmount(total);
            billRepo.updateBill(bill);
        }

        return bill;
    }

    @Override
    public Bill getBillById(Long billId) {
        return billRepo.getBillById(billId);
    }

    @Override
    public boolean confirmPayment(Long billId, String status) {
        Bill bill = billRepo.getBillById(billId);
        if (bill == null) return false;
        bill.setPaymentStatus(status); // "CONFIRMED" hoặc "REJECTED"
        bill.setUpdatedAt(new Date());
        billRepo.updateBill(bill);
        return true;
    }

    @Override
    public List<Bill> getBillsByUser(User user) {
        return billRepo.getBillsByResident(user);
    }

    @Override
    public Bill getUserBillById(User user, Long billId) {
        return billRepo.getBillByIdAndResident(billId, user);
    }

    @Override
    public Bill uploadPaymentProof(User user, Long billId, MultipartFile file) {
        Bill bill = billRepo.getBillByIdAndResident(billId, user);
        if (bill == null) return null;
        if (file != null && !file.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                bill.setPaymentProof(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(BillServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        bill.setPaymentStatus("PENDING");
        bill.setUpdatedAt(new Date());
        billRepo.updateBill(bill);
        return bill;
    }

    @Override
    public Bill createOnlinePayment(User user, Long billId, Map<String, String> params) {
        Bill bill = billRepo.getBillByIdAndResident(billId, user);
        if (bill == null) return null;
        //Todo: Xử lý thanh toán online
        bill.setPaymentMethod("ONLINE");
        bill.setPaymentStatus("PENDING");
        bill.setUpdatedAt(new Date());
        billRepo.updateBill(bill);
        return bill;
    }
    @Override
    public List<FeeType> getAllFeeTypes() {
        return feeTypeRepo.getAllFeeTypes();
    }

    @Override
    public Bill createBill(com.mycompany.dto.CreateBillDTO billDTO) {
        Logger.getLogger(BillServiceImpl.class.getName()).log(Level.INFO, 
            "Starting bill creation for apartment ID: " + billDTO.getApartmentId());
            
        Bill bill = new Bill();
        bill.setPaymentStatus("UNPAID");
        
        // Set timestamps
        Date now = new Date();
        bill.setCreatedAt(now);
        bill.setUpdatedAt(now);

        bill.setMonth(billDTO.getMonth());
        bill.setYear(billDTO.getYear());
        // Initialize required totalAmount field with 0
        bill.setTotalAmount(BigDecimal.ZERO);
        
        // Verify apartment exists
        Apartment apartment = apartmentRepo.getApartmentById(billDTO.getApartmentId());
        if (apartment == null) {
            Logger.getLogger(BillServiceImpl.class.getName()).log(Level.SEVERE, 
                "Cannot create bill - apartment not found: " + billDTO.getApartmentId());
            return null;
        }
        bill.setApartmentId(apartment);

        // Save bill first to get billId
        bill = billRepo.addBill(bill);
        if (bill == null) {
            Logger.getLogger(BillServiceImpl.class.getName()).log(Level.SEVERE, 
                "Failed to save initial bill state. Check database configuration and connectivity.");
            return null;
        }

        Logger.getLogger(BillServiceImpl.class.getName()).log(Level.INFO, 
            "Created bill with ID: " + bill.getBillId());

        BigDecimal total = BigDecimal.ZERO;

        // Process bill details
        for (com.mycompany.dto.BillDetailDTO d : billDTO.getBillDetails()) {
            if (d.getFeeTypeId() == null || d.getQuantity() == null || d.getUnitPrice() == null) {
                return null;
            }
            
            FeeType feeType = feeTypeRepo.getFeeTypeById(d.getFeeTypeId());
            if (feeType == null) {
                return null;
            }

            BillDetail detail = new BillDetail();
            detail.setFeeTypeId(feeType);
            detail.setBillId(bill);
            detail.setQuantity(d.getQuantity());
            detail.setUnitPrice(d.getUnitPrice());
            detail.setAmount(detail.getQuantity().multiply(detail.getUnitPrice()));
            detail.setNote(d.getNote());

            // Add to total
            total = total.add(detail.getAmount());

            // Save bill detail
            BillDetail savedDetail = billDetailRepo.addBillDetail(detail);
            if (savedDetail == null) {
                return null;
            }
        }

        // Update total amount
        if (bill == null) {
            return null;
        }
        
        bill.setTotalAmount(total);
        billRepo.updateBill(bill);

        return bill;
    }

    @Override
    public List<BillDetail> getBillDetails(Long billId) {
        return billDetailRepo.getBillDetailsByBillId(billId);
    }

    @Override
    public Bill findByApartmentAndMonthAndYear(Long apartmentId, short month, int year) {
        return billRepo.findByApartmentAndMonthAndYear(apartmentId, month, year);
    }
}
