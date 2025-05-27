/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlychungcu;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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

        // Create user 1: Resident (active)
       User user1 = session.get(User.class, 1L);

//        user1.setUsername("testuser");
//        user1.setPassword(new BCryptPasswordEncoder().encode("test123"));
//        user1.setFullName("Test User");
//        user1.setEmail("testuser@example.com");
//        user1.setPhone("0123456789");
//        user1.setRole("RESIDENT");
//        user1.setStatus("ACTIVE");
//        session.persist(user1);

        // Create user 2: Admin (active)
        User user2 =  session.get(User.class, 2L);
        // user2.setUsername("adminuser");
        // user2.setPassword(new BCryptPasswordEncoder().encode("admin123"));
        // user2.setFullName("Admin User");
        // user2.setEmail("admin@example.com");
        // user2.setPhone("0987654321");
        // user2.setRole("ADMIN");
        // user2.setStatus("ACTIVE");
        // session.persist(user2);

        // Create user 3: Resident (inactive)
        User user3 =  session.get(User.class, 3L);
        // user3.setUsername("inactiveuser");
        // user3.setPassword(new BCryptPasswordEncoder().encode("inactive123"));
        // user3.setFullName("Inactive User");
        // user3.setEmail("inactive@example.com");
        // user3.setPhone("0111222333");
        // user3.setRole("RESIDENT");
        // user3.setStatus("INACTIVE");
        // session.persist(user3);

        // Create user 4: Resident (active)
        User user4 = session.get(User.class, 4L);
        // user4.setUsername("resident2");
        // user4.setPassword(new BCryptPasswordEncoder().encode("resident2123"));
        // user4.setFullName("Resident Two");
        // user4.setEmail("resident2@example.com");
        // user4.setPhone("0222333444");
        // user4.setRole("RESIDENT");
        // user4.setStatus("ACTIVE");
        // session.persist(user4);

        // Insert an apartment
        Apartment apartment = session.get(Apartment.class, 1L);
        // apartment.setApartmentCode("A101");
        // apartment.setFloor(1);
        // apartment.setBlock("A");
        // apartment.setStatus("AVAILABLE");
        // session.persist(apartment);

        // Insert a vehicle for user1
        Vehicle vehicle = session.get(Vehicle.class, 1L);
        // vehicle.setLicensePlate("59A-12345");
        // vehicle.setVehicleType("CAR");
        // vehicle.setCreatedAt(new java.util.Date());
        // vehicle.setStatus(true);
        // vehicle.setResidentId(user1);
        // session.persist(vehicle);

        // Insert a bill for user1 and apartment
        Bill bill = session.get(Bill.class, 1L);
        // bill.setMonth((short)5);
        // bill.setYear(2025);
        // bill.setTotalAmount(new java.math.BigDecimal("1500000"));
        // bill.setPaymentStatus("UNPAID");
        // bill.setCreatedAt(new java.util.Date());
        // bill.setApartmentId(apartment);
        // bill.setPaidByResidentId(user1);
        // session.persist(bill);

        // --- MOCKUP SURVEY DATA ---
        // 1. Tạo khảo sát
        Survey survey = new Survey();
        survey.setTitle("Khảo sát mức độ hài lòng về dịch vụ");
        survey.setDescription("Vui lòng đánh giá các dịch vụ của chung cư");
        survey.setStatus("ACTIVE");
        survey.setCreatedAt(new java.util.Date());
        survey.setStartDate(new java.util.Date());
        survey.setEndDate(new java.util.Date(System.currentTimeMillis() + 7*24*60*60*1000L)); // 7 ngày
        survey.setCreatedByAdminId(user2); // admin

        // 2. Tạo câu hỏi cho khảo sát
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

        // 3. Tạo phản hồi khảo sát cho user1
        SurveyResponse response = new SurveyResponse();
        response.setSurveyId(survey);
        response.setResidentId(user1);
        response.setSubmittedAt(new java.util.Date());

        // Trả lời cho từng câu hỏi
        ResponseDetail d1 = new ResponseDetail();
        d1.setQuestionId(q1);
        d1.setOptionId(opt1); // chọn "Rất hài lòng"
        d1.setResponseId(response);

        ResponseDetail d2 = new ResponseDetail();
        d2.setQuestionId(q2);
        d2.setRatingValue(5); // 5 sao
        d2.setResponseId(response);

        ResponseDetail d3 = new ResponseDetail();
        d3.setQuestionId(q3);
        d3.setTextAnswer("Không có góp ý thêm");
        d3.setResponseId(response);

        java.util.List<ResponseDetail> details = java.util.Arrays.asList(d1, d2, d3);
        response.setResponseDetailCollection(details);
        session.persist(response);

        session.getTransaction().commit();
        session.close();
        factory.close();
        System.out.println("Users and related data inserted!");
    }
}
