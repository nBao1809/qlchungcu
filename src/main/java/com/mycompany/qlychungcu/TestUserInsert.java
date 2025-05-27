/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlychungcu;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mycompany.pojo.Apartment;
import com.mycompany.pojo.Bill;
import com.mycompany.pojo.QuestionOption;
import com.mycompany.pojo.ResponseDetail;
import com.mycompany.pojo.Survey;
import com.mycompany.pojo.SurveyQuestion;
import com.mycompany.pojo.SurveyResponse;
import com.mycompany.pojo.User;
import com.mycompany.pojo.Vehicle;

public class TestUserInsert {
    
    public static void main(String[] args) {
        // Hibernate config
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();

        // Xóa toàn bộ dữ liệu mockup (theo thứ tự tránh lỗi khóa ngoại)
        deleteAllMockupData(session);

        // Thêm lại dữ liệu mockup
        insertMockupData(session);

        session.getTransaction().commit();
        session.close();
        factory.close();
        System.out.println("Users and related data inserted!");
    }

    // Xóa toàn bộ dữ liệu mockup (theo thứ tự phụ thuộc khóa ngoại)
    public static void deleteAllMockupData(Session session) {
        // Xóa chi tiết hóa đơn
        session.createQuery("delete from BillDetail").executeUpdate();
        // Xóa loại phí
        session.createQuery("delete from FeeType").executeUpdate();
        // Xóa liên kết cư dân - căn hộ
        session.createQuery("delete from ResidentApartment").executeUpdate();
        // Xóa người thân
        session.createQuery("delete from Relative").executeUpdate();
        // Xóa tủ đồ
        session.createQuery("delete from LockerItem").executeUpdate();
        // Xóa khiếu nại
        session.createQuery("delete from Complaint").executeUpdate();
        // Xóa phản hồi khảo sát chi tiết
        session.createQuery("delete from ResponseDetail").executeUpdate();
        // Xóa phản hồi khảo sát
        session.createQuery("delete from SurveyResponse").executeUpdate();
        // Xóa lựa chọn câu hỏi
        session.createQuery("delete from QuestionOption").executeUpdate();
        // Xóa câu hỏi khảo sát
        session.createQuery("delete from SurveyQuestion").executeUpdate();
        // Xóa khảo sát
        session.createQuery("delete from Survey").executeUpdate();
        // Xóa hóa đơn
        session.createQuery("delete from Bill").executeUpdate();
        // Xóa phương tiện
        session.createQuery("delete from Vehicle").executeUpdate();
        // Xóa căn hộ
        session.createQuery("delete from Apartment").executeUpdate();
        // Xóa user
        session.createQuery("delete from User").executeUpdate();
    }

    // Thêm dữ liệu mockup
    public static void insertMockupData(Session session) {
        // --- USERS ---
        User user1 = new User();
        user1.setUsername("testuser");
        user1.setPassword(new BCryptPasswordEncoder().encode("test123"));
        user1.setFullName("Test User");
        user1.setEmail("testuser@example.com");
        user1.setPhone("0123456789");
        user1.setRole("RESIDENT");
        user1.setStatus("ACTIVE");
        session.persist(user1);

        User user2 = new User();
        user2.setUsername("adminuser");
        user2.setPassword(new BCryptPasswordEncoder().encode("admin123"));
        user2.setFullName("Admin User");
        user2.setEmail("admin@example.com");
        user2.setPhone("0987654321");
        user2.setRole("ADMIN");
        user2.setStatus("ACTIVE");
        session.persist(user2);

        User user3 = new User();
        user3.setUsername("inactiveuser");
        user3.setPassword(new BCryptPasswordEncoder().encode("inactive123"));
        user3.setFullName("Inactive User");
        user3.setEmail("inactive@example.com");
        user3.setPhone("0111222333");
        user3.setRole("RESIDENT");
        user3.setStatus("INACTIVE");
        session.persist(user3);

        User user4 = new User();
        user4.setUsername("resident2");
        user4.setPassword(new BCryptPasswordEncoder().encode("resident123"));
        user4.setFullName("Resident Two");
        user4.setEmail("resident2@example.com");
        user4.setPhone("0222333444");
        user4.setRole("RESIDENT");
        user4.setStatus("ACTIVE");
        session.persist(user4);

        // --- APARTMENTS ---
        Apartment apartment1 = new Apartment();
        apartment1.setApartmentCode("A101");
        apartment1.setFloor(1);
        apartment1.setBlock("A");
        apartment1.setStatus("AVAILABLE");
        session.persist(apartment1);

        Apartment apartment2 = new Apartment();
        apartment2.setApartmentCode("B202");
        apartment2.setFloor(2);
        apartment2.setBlock("B");
        apartment2.setStatus("OCCUPIED");
        session.persist(apartment2);

        // --- VEHICLES ---
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setLicensePlate("59A-12345");
        vehicle1.setVehicleType("CAR");
        vehicle1.setCreatedAt(new java.util.Date());
        vehicle1.setStatus(true);
        vehicle1.setResidentId(user1);
        session.persist(vehicle1);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setLicensePlate("59B-67890");
        vehicle2.setVehicleType("MOTORBIKE");
        vehicle2.setCreatedAt(new java.util.Date());
        vehicle2.setStatus(true);
        vehicle2.setResidentId(user4);
        session.persist(vehicle2);

        // --- BILLS ---
        Bill bill1 = new Bill();
        bill1.setMonth((short)5);
        bill1.setYear(2025);
        bill1.setTotalAmount(new java.math.BigDecimal("1500000"));
        bill1.setPaymentStatus("UNPAID");
        bill1.setCreatedAt(new java.util.Date());
        bill1.setApartmentId(apartment1);
        bill1.setPaidByResidentId(user1);
        session.persist(bill1);

        Bill bill2 = new Bill();
        bill2.setMonth((short)6);
        bill2.setYear(2025);
        bill2.setTotalAmount(new java.math.BigDecimal("2000000"));
        bill2.setPaymentStatus("PAID");
        bill2.setCreatedAt(new java.util.Date());
        bill2.setApartmentId(apartment2);
        bill2.setPaidByResidentId(user4);
        session.persist(bill2);

        // --- RELATIVES ---
        com.mycompany.pojo.Relative relative1 = new com.mycompany.pojo.Relative();
        relative1.setFullName("Nguyen Van A");
        relative1.setRelationship("Bố");
        relative1.setPhone("0909123456");
        relative1.setCccd("123456789");
        relative1.setHasAccessCard(true);
        relative1.setHasVehicleCard(false);
        relative1.setCreatedAt(new java.util.Date());
        relative1.setStatus(true);
        relative1.setResidentId(user1);
        session.persist(relative1);

        com.mycompany.pojo.Relative relative2 = new com.mycompany.pojo.Relative();
        relative2.setFullName("Tran Thi B");
        relative2.setRelationship("Mẹ");
        relative2.setPhone("0911222333");
        relative2.setCccd("987654321");
        relative2.setHasAccessCard(false);
        relative2.setHasVehicleCard(true);
        relative2.setCreatedAt(new java.util.Date());
        relative2.setStatus(true);
        relative2.setResidentId(user4);
        session.persist(relative2);

        // --- LOCKER ITEMS ---
        com.mycompany.pojo.LockerItem locker1 = new com.mycompany.pojo.LockerItem();
        locker1.setItemName("Tủ đồ tầng 1");
        locker1.setDescription("Đựng vật dụng cá nhân");
        locker1.setReceivedDate(new java.util.Date());
        locker1.setStatus("RECEIVED");
        locker1.setNote("Không có ghi chú");
        locker1.setResidentId(user1);
        locker1.setReceivedByAdminId(user2);
        session.persist(locker1);

        com.mycompany.pojo.LockerItem locker2 = new com.mycompany.pojo.LockerItem();
        locker2.setItemName("Tủ đồ tầng 2");
        locker2.setDescription("Đựng vật dụng chung");
        locker2.setReceivedDate(new java.util.Date());
        locker2.setStatus("DELIVERED");
        locker2.setNote("Đã trả cho cư dân");
        locker2.setResidentId(user4);
        locker2.setReceivedByAdminId(user2);
        session.persist(locker2);

        // --- COMPLAINTS ---
        com.mycompany.pojo.Complaint complaint1 = new com.mycompany.pojo.Complaint();
        complaint1.setTitle("Nước yếu");
        complaint1.setContent("Nước sinh hoạt rất yếu ở phòng A101");
        complaint1.setStatus("PENDING");
        complaint1.setSubmittedDate(new java.util.Date());
        complaint1.setResidentId(user1);
        complaint1.setResolutionNote(null);
        session.persist(complaint1);

        com.mycompany.pojo.Complaint complaint2 = new com.mycompany.pojo.Complaint();
        complaint2.setTitle("Thang máy hỏng");
        complaint2.setContent("Thang máy block B không hoạt động");
        complaint2.setStatus("RESOLVED");
        complaint2.setSubmittedDate(new java.util.Date());
        complaint2.setResidentId(user4);
        complaint2.setResolutionNote("Đã sửa xong");
        complaint2.setResolvedDate(new java.util.Date());
        complaint2.setResolvedByAdminId(user2);
        session.persist(complaint2);

        // --- SURVEY & QUESTIONS ---
        Survey survey = new Survey();
        survey.setTitle("Khảo sát mức độ hài lòng về dịch vụ");
        survey.setDescription("Vui lòng đánh giá các dịch vụ của chung cư");
        survey.setStatus("ACTIVE");
        survey.setCreatedAt(new java.util.Date());
        survey.setStartDate(new java.util.Date());
        survey.setEndDate(new java.util.Date(System.currentTimeMillis() + 7*24*60*60*1000L));
        survey.setCreatedByAdminId(user2);

        SurveyQuestion q1 = new SurveyQuestion();
        q1.setQuestionText("Bạn hài lòng với dịch vụ bảo vệ không?");
        q1.setQuestionType("SINGLE_CHOICE");
        q1.setRequired(true);
        q1.setOrderNumber(1);
        q1.setSurveyId(survey);

        QuestionOption opt1 = new QuestionOption();
        opt1.setOptionText("Rất hài lòng");
        opt1.setOrderNumber(1);
        opt1.setQuestionId(q1);
        QuestionOption opt2 = new QuestionOption();
        opt2.setOptionText("Bình thường");
        opt2.setOrderNumber(2);
        opt2.setQuestionId(q1);
        QuestionOption opt3 = new QuestionOption();
        opt3.setOptionText("Không hài lòng");
        opt3.setOrderNumber(3);
        opt3.setQuestionId(q1);
        java.util.List<QuestionOption> options1 = java.util.Arrays.asList(opt1, opt2, opt3);
        q1.setQuestionOptionCollection(options1);

        SurveyQuestion q2 = new SurveyQuestion();
        q2.setQuestionText("Bạn đánh giá thế nào về vệ sinh chung cư?");
        q2.setQuestionType("RATING");
        q2.setRequired(true);
        q2.setOrderNumber(2);
        q2.setSurveyId(survey);
        q2.setQuestionOptionCollection(null);

        SurveyQuestion q3 = new SurveyQuestion();
        q3.setQuestionText("Ý kiến đóng góp khác");
        q3.setQuestionType("TEXT");
        q3.setRequired(false);
        q3.setOrderNumber(3);
        q3.setSurveyId(survey);
        q3.setQuestionOptionCollection(null);

        java.util.List<SurveyQuestion> questions = java.util.Arrays.asList(q1, q2, q3);
        survey.setSurveyQuestionCollection(questions);
        session.persist(survey);

        // --- SURVEY RESPONSE ---
        SurveyResponse response = new SurveyResponse();
        response.setSurveyId(survey);
        response.setResidentId(user1);
        response.setSubmittedAt(new java.util.Date());

        ResponseDetail d1 = new ResponseDetail();
        d1.setQuestionId(q1);
        d1.setOptionId(opt1);
        d1.setResponseId(response);

        ResponseDetail d2 = new ResponseDetail();
        d2.setQuestionId(q2);
        d2.setRatingValue(5);
        d2.setResponseId(response);

        ResponseDetail d3 = new ResponseDetail();
        d3.setQuestionId(q3);
        d3.setTextAnswer("Không có góp ý thêm");
        d3.setResponseId(response);

        java.util.List<ResponseDetail> details = java.util.Arrays.asList(d1, d2, d3);
        response.setResponseDetailCollection(details);
        session.persist(response);

        // --- FEE TYPES ---
        com.mycompany.pojo.FeeType feeType1 = new com.mycompany.pojo.FeeType();
        feeType1.setFeeName("Phí quản lý");
        feeType1.setDescription("Phí quản lý chung cư hàng tháng");
        feeType1.setAmountPerUnit(new java.math.BigDecimal("10000"));
        feeType1.setUnit("m2");
        feeType1.setCreatedAt(new java.util.Date());
        feeType1.setStatus(true);
        session.persist(feeType1);

        com.mycompany.pojo.FeeType feeType2 = new com.mycompany.pojo.FeeType();
        feeType2.setFeeName("Phí gửi xe");
        feeType2.setDescription("Phí gửi xe máy/tháng");
        feeType2.setAmountPerUnit(new java.math.BigDecimal("120000"));
        feeType2.setUnit("xe");
        feeType2.setCreatedAt(new java.util.Date());
        feeType2.setStatus(true);
        session.persist(feeType2);

        // --- BILL DETAILS ---
        com.mycompany.pojo.BillDetail billDetail1 = new com.mycompany.pojo.BillDetail();
        billDetail1.setBillId(bill1);
        billDetail1.setFeeTypeId(feeType1);
        billDetail1.setQuantity(new java.math.BigDecimal("80"));
        billDetail1.setUnitPrice(new java.math.BigDecimal("10000"));
        billDetail1.setAmount(new java.math.BigDecimal("800000"));
        billDetail1.setNote("Phí quản lý tháng 5");
        session.persist(billDetail1);

        com.mycompany.pojo.BillDetail billDetail2 = new com.mycompany.pojo.BillDetail();
        billDetail2.setBillId(bill1);
        billDetail2.setFeeTypeId(feeType2);
        billDetail2.setQuantity(new java.math.BigDecimal("1"));
        billDetail2.setUnitPrice(new java.math.BigDecimal("120000"));
        billDetail2.setAmount(new java.math.BigDecimal("120000"));
        billDetail2.setNote("Phí gửi xe tháng 5");
        session.persist(billDetail2);

        com.mycompany.pojo.BillDetail billDetail3 = new com.mycompany.pojo.BillDetail();
        billDetail3.setBillId(bill2);
        billDetail3.setFeeTypeId(feeType1);
        billDetail3.setQuantity(new java.math.BigDecimal("90"));
        billDetail3.setUnitPrice(new java.math.BigDecimal("10000"));
        billDetail3.setAmount(new java.math.BigDecimal("900000"));
        billDetail3.setNote("Phí quản lý tháng 6");
        session.persist(billDetail3);

        com.mycompany.pojo.BillDetail billDetail4 = new com.mycompany.pojo.BillDetail();
        billDetail4.setBillId(bill2);
        billDetail4.setFeeTypeId(feeType2);
        billDetail4.setQuantity(new java.math.BigDecimal("2"));
        billDetail4.setUnitPrice(new java.math.BigDecimal("120000"));
        billDetail4.setAmount(new java.math.BigDecimal("240000"));
        billDetail4.setNote("Phí gửi xe tháng 6");
        session.persist(billDetail4);

        // --- RESIDENT APARTMENT ---
        com.mycompany.pojo.ResidentApartmentPK pk1 = new com.mycompany.pojo.ResidentApartmentPK();
        pk1.setResidentId(user1.getId());
        pk1.setApartmentId(apartment1.getApartmentId());
        com.mycompany.pojo.ResidentApartment ra1 = new com.mycompany.pojo.ResidentApartment();
        ra1.setResidentApartmentPK(pk1);
        ra1.setUser(user1);
        ra1.setApartment(apartment1);
        ra1.setRole("OWNER");
        ra1.setCreatedAt(new java.util.Date());
        session.persist(ra1);

        com.mycompany.pojo.ResidentApartmentPK pk2 = new com.mycompany.pojo.ResidentApartmentPK();
        pk2.setResidentId(user4.getId());
        pk2.setApartmentId(apartment2.getApartmentId());
        com.mycompany.pojo.ResidentApartment ra2 = new com.mycompany.pojo.ResidentApartment();
        ra2.setResidentApartmentPK(pk2);
        ra2.setUser(user4);
        ra2.setApartment(apartment2);
        ra2.setRole("OWNER");
        ra2.setCreatedAt(new java.util.Date());
        session.persist(ra2);
    }
}
