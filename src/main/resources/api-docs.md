# API Tổng hợp toàn bộ hệ thống QLyChungCu

---

## 1. Quản lý người dùng (User)

### Đăng ký cư dân
- **POST** `/api/users`
- **Request:** multipart/form-data (params + avatar)
- **Response:** User JSON

### Đăng nhập
- **POST** `/api/login`
- **Request:** username, password (form-data)
- **Response:** `{ "token": "..." }`

### Đổi mật khẩu
- **POST** `/api/change-password`
- **Request:** `{ "newPassword": "..." }`
- **Response:** Thông báo

### Cập nhật avatar
- **POST** `/api/update-avatar`
- **Request:** multipart/form-data (avatar)
- **Response:** Thông báo

### Lấy profile
- **GET** `/api/secure/profile`
- **Response:** User JSON

### Quản trị viên: Quản lý user
- **GET** `/api/admin/users?role=...`
- **POST** `/api/admin/users` (tạo user mới)
- **PUT** `/api/admin/users/{userId}` (cập nhật)
- **PUT** `/api/admin/users/{userId}/toggle-status` (khóa/mở)

---

## 2. Quản lý căn hộ (Apartment)

### Quản trị viên
- **GET** `/api/admin/apartments` (danh sách)
- **POST** `/api/admin/apartments` (thêm mới)
- **PUT** `/api/admin/apartments/{apartmentId}` (cập nhật)
- **GET** `/api/admin/apartments/{apartmentId}` (chi tiết + cư dân)

---

## 3. Quản lý phương tiện (Vehicle)

### Quản trị viên
- **GET** `/api/admin/vehicles` (danh sách)

### Cư dân
- **GET** `/api/users/vehicles` (danh sách xe của mình + người thân)
- **POST** `/api/users/vehicles` (thêm mới)
- **PUT** `/api/users/vehicles/{vehicleId}` (cập nhật)

---

## 4. Quản lý người thân (Relative)

### Quản trị viên
- **GET** `/api/admin/relatives` (danh sách)

### Cư dân
- **GET** `/api/users/relatives` (danh sách người thân của mình)
- **POST** `/api/users/relatives` (thêm mới)
- **PUT** `/api/users/relatives/{relativeId}` (cập nhật)

---

## 5. Quản lý tủ đồ (Locker Item)

### Quản trị viên
- **GET** `/api/admin/locker-items` (danh sách)
- **POST** `/api/admin/locker-items` (thêm mới)
- **PUT** `/api/admin/locker-items/{itemId}` (cập nhật trạng thái)

### Cư dân
- **GET** `/api/users/locker-items` (danh sách tủ đồ của mình)

---

## 6. Quản lý khiếu nại (Complaint)

### Quản trị viên
- **GET** `/api/admin/complaints` (danh sách)
- **PUT** `/api/admin/complaints/{id}` (cập nhật trạng thái)
- **GET** `/api/complaints/{id}` (chi tiết)

### Cư dân
- **GET** `/api/users/complaints` (danh sách của mình)
- **POST** `/api/users/complaints` (gửi khiếu nại, có thể kèm ảnh)

---

## 7. Quản lý khảo sát (Survey)

### Quản trị viên
- **GET** `/api/admin/surveys` (danh sách)
- **POST** `/api/admin/surveys` (tạo mới)
- **PUT** `/api/admin/surveys/{surveyId}/status` (đổi trạng thái)
- **GET** `/api/admin/surveys/{surveyId}` (chi tiết)
- **GET** `/api/admin/surveys/{surveyId}/results` (kết quả)

### Cư dân
- **GET** `/api/users/surveys` (danh sách khảo sát có thể tham gia)
- **GET** `/api/users/surveys/{surveyId}` (chi tiết khảo sát)
- **POST** `/api/users/surveys/{surveyId}/respond` (gửi phản hồi)

---

## 8. Quản lý hóa đơn (Bill)

### Quản trị viên
- **GET** `/api/admin/bills` (danh sách, lọc, phân trang)
- **POST** `/api/admin/bills` (tạo mới)
- **PUT** `/api/admin/bills/{billId}` (cập nhật)
- **DELETE** `/api/admin/bills/{billId}` (xóa)
- **GET** `/api/admin/bills/{billId}` (chi tiết)
- **PUT** `/api/admin/bills/{billId}/confirm-payment` (xác nhận thanh toán)
- **GET** `/api/admin/bills/fee-types` (danh sách loại phí)
- **GET** `/api/admin/bills/{billId}/details` (chi tiết + các khoản phí)

### Cư dân
- **GET** `/api/users/bills` (danh sách của mình, lọc, phân trang)
- **GET** `/api/users/bills/{billId}` (chi tiết của mình)
- **GET** `/api/users/bills/{billId}/details` (chi tiết + các khoản phí)
- **POST** `/api/users/bills/{billId}/payment-proof` (upload minh chứng chuyển khoản)
- **POST** `/api/users/bills/{billId}/online-payment` (tạo giao dịch online)

---

## 9. Mẫu lỗi chung

- Không tìm thấy: `{ "error": "Không tìm thấy ..." }`
- Lỗi xác thực/JWT: `{ "error": "Token không hợp lệ hoặc hết hạn" }`
- Lỗi quyền: `{ "error": "Bạn không có quyền thực hiện thao tác này" }`

---

## 10. Lưu ý
- Các API có phân quyền rõ ràng (admin/cư dân).
- Các API có thể trả về mã lỗi HTTP phù hợp (400, 401, 403, 404, 500).
- Tham khảo chi tiết mẫu JSON từng API ở phần trên hoặc trong code controller/service.
