# API Tổng hợp toàn bộ hệ thống QLyChungCu

---

## 1. Quản lý người dùng (User)

### Đăng ký cư dân
- **POST** `/api/users`
- **Request:** multipart/form-data
  - username: string
  - password: string
  - fullName: string
  - avatar: file (optional)
- **Response:**
```json
{
  "id": 1,
  "username": "resident1",
  "fullName": "Nguyen Van A",
  "role": "ROLE_USER",
  ...
}
```

### Đăng nhập
- **POST** `/api/login`
- **Request:** form-data
  - username: string
  - password: string
- **Response:**
```json
{ "token": "<jwt-token>" }
```

### Đổi mật khẩu
- **POST** `/api/change-password`
- **Request:**
```json
{ "newPassword": "654321" }
```
- **Response:**
```json
"Đổi mật khẩu thành công"
```

### Cập nhật avatar
- **POST** `/api/update-avatar`
- **Request:** multipart/form-data
  - avatar: file
- **Response:**
```json
"Cập nhật avatar thành công"
```

### Lấy profile
- **GET** `/api/secure/profile`
- **Response:**
```json
{
  "id": 1,
  "username": "resident1",
  "fullName": "Nguyen Van A",
  "role": "ROLE_USER",
  ...
}
```

### Quản trị viên: Quản lý user
- **GET** `/api/admin/users?role=...`
  - **Response:** List User JSON
- **POST** `/api/admin/users` (tạo user mới)
  - **Request:** multipart/form-data (như đăng ký cư dân, thêm role)
  - **Response:** User JSON
- **PUT** `/api/admin/users/{userId}` (cập nhật)
  - **Request:**
```json
{ "fullName": "Tên mới", ... }
```
  - **Response:** User JSON
- **PUT** `/api/admin/users/{userId}/toggle-status` (khóa/mở)
  - **Response:**
```json
"Cập nhật trạng thái thành công"
```

---

## 2. Quản lý căn hộ (Apartment)

### Quản trị viên
- **GET** `/api/admin/apartments`
  - **Response:** List Apartment JSON
- **POST** `/api/admin/apartments`
  - **Request:**
```json
{ "apartmentCode": "A101", "floor": "1", "block": "A" }
```
  - **Response:** Apartment JSON
- **PUT** `/api/admin/apartments/{apartmentId}`
  - **Request:**
```json
{ "floor": "2" }
```
  - **Response:** Apartment JSON
- **GET** `/api/admin/apartments/{apartmentId}`
  - **Response:**
```json
{
  "apartment": { ... },
  "residents": [ { ... }, ... ]
}
```

---

## 3. Quản lý phương tiện (Vehicle)

### Quản trị viên
- **GET** `/api/admin/vehicles`
  - **Response:** List Vehicle JSON

### Cư dân
- **GET** `/api/users/vehicles`
  - **Response:** List Vehicle JSON
- **POST** `/api/users/vehicles`
  - **Request:**
```json
{ "licensePlate": "29A-12345", "vehicleType": "Car" }
```
  - **Response:** Vehicle JSON
- **PUT** `/api/users/vehicles/{vehicleId}`
  - **Request:**
```json
{ "vehicleType": "Motorbike" }
```
  - **Response:** Vehicle JSON

---

## 4. Quản lý người thân (Relative)

### Quản trị viên
- **GET** `/api/admin/relatives`
  - **Response:** List Relative JSON

### Cư dân
- **GET** `/api/users/relatives`
  - **Response:** List Relative JSON
- **POST** `/api/users/relatives`
  - **Request:**
```json
{ "fullName": "Nguyen Van B", "relationship": "Anh trai" }
```
  - **Response:** Relative JSON
- **PUT** `/api/users/relatives/{relativeId}`
  - **Request:**
```json
{ "relationship": "Em gái" }
```
  - **Response:** Relative JSON

---

## 5. Quản lý tủ đồ (Locker Item)

### Quản trị viên
- **GET** `/api/admin/locker-items`
  - **Response:** List LockerItem JSON
- **POST** `/api/admin/locker-items`
  - **Request:**
```json
{ "name": "Tủ đồ A", ... }
```
  - **Response:** LockerItem JSON
- **PUT** `/api/admin/locker-items/{itemId}`
  - **Request:**
```json
{ "status": "IN_USE" }
```
  - **Response:** LockerItem JSON

### Cư dân
- **GET** `/api/users/locker-items`
  - **Response:** List LockerItem JSON

---

## 6. Quản lý khiếu nại (Complaint)

### Quản trị viên
- **GET** `/api/admin/complaints`
  - **Response:** List Complaint JSON
- **PUT** `/api/admin/complaints/{id}`
  - **Request:**
```json
{ "status": "RESOLVED" }
```
  - **Response:** Complaint JSON
- **GET** `/api/complaints/{id}`
  - **Response:** Complaint JSON

### Cư dân
- **GET** `/api/users/complaints`
  - **Response:** List Complaint JSON
- **POST** `/api/users/complaints`
  - **Request:** multipart/form-data
    - title: string
    - content: string
    - imageProof: file (optional)
  - **Response:** Complaint JSON
- **PUT** `/api/users/complaints/{complaintId}`
  - **Request:**
```json
{ "content": "Nội dung cập nhật khiếu nại" }
```
  - **Response:** Complaint JSON

---

## 7. Quản lý khảo sát (Survey)

### Quản trị viên
- **GET** `/api/admin/surveys`
  - **Response:** List Survey JSON
- **POST** `/api/admin/surveys`
  - **Request:**
```json
{ "title": "Khảo sát mới", "content": "Nội dung khảo sát" }
```
  - **Response:** Survey JSON
- **PUT** `/api/admin/surveys/{surveyId}/status`
  - **Request:**
```json
{ "status": "ACTIVE" }
```
  - **Response:** Survey JSON
- **GET** `/api/admin/surveys/{surveyId}`
  - **Response:** Survey JSON
- **GET** `/api/admin/surveys/{surveyId}/results`
  - **Response:**
```json
{
  "survey": { ... },
  "results": [ ... ]
}
```

### Cư dân
- **GET** `/api/users/surveys`
  - **Response:** List Survey JSON
- **GET** `/api/users/surveys/{surveyId}`
  - **Response:** Survey JSON
- **POST** `/api/users/surveys/{surveyId}/respond`
  - **Request:**
```json
{ "answers": [ ... ] }
```
  - **Response:** SurveyResponse JSON

---

## 8. Quản lý hóa đơn (Bill)

### Quản trị viên
- **GET** `/api/admin/bills`
  - **Response:** List Bill JSON
- **POST** `/api/admin/bills`
  - **Request:**
```json
{ "apartmentId": 1, "totalAmount": 1000000, "month": 5, "year": 2025 }
```
  - **Response:** Bill JSON
- **PUT** `/api/admin/bills/{billId}`
  - **Request:**
```json
{ "totalAmount": 2000000 }
```
  - **Response:** Bill JSON
- **DELETE** `/api/admin/bills/{billId}`
  - **Response:**
```json
"Xóa hóa đơn thành công"
```
- **GET** `/api/admin/bills/{billId}`
  - **Response:** Bill JSON
- **PUT** `/api/admin/bills/{billId}/confirm-payment`
  - **Request:**
```json
{ "status": "PAID" }
```
  - **Response:** Bill JSON
- **GET** `/api/admin/bills/fee-types`
  - **Response:** List FeeType JSON
- **GET** `/api/admin/bills/{billId}/details`
  - **Response:**
```json
{
  "bill": { ... },
  "details": [ ... ]
}
```

### Cư dân
- **GET** `/api/users/bills`
  - **Response:** List Bill JSON
- **GET** `/api/users/bills/{billId}`
  - **Response:** Bill JSON
- **GET** `/api/users/bills/{billId}/details`
  - **Response:**
```json
{
  "bill": { ... },
  "details": [ ... ]
}
```
- **POST** `/api/users/bills/{billId}/payment-proof`
  - **Request:** multipart/form-data
    - file: file
  - **Response:** Bill JSON
- **POST** `/api/users/bills/{billId}/online-payment`
  - **Request:**
```json
{ "paymentMethod": "VNPAY" }
```
  - **Response:** Bill JSON (hoặc thông tin giao dịch online)

---

## 9. Mẫu lỗi chung

- Không tìm thấy:
```json
{ "error": "Không tìm thấy ..." }
```
- Lỗi xác thực/JWT:
```json
{ "error": "Token không hợp lệ hoặc hết hạn" }
```
- Lỗi quyền:
```json
{ "error": "Bạn không có quyền thực hiện thao tác này" }
```

---

## 10. Lưu ý
- Các API có phân quyền rõ ràng (admin/cư dân).
- Các API có thể trả về mã lỗi HTTP phù hợp (400, 401, 403, 404, 500).
- Tham khảo chi tiết mẫu JSON từng API ở phần trên hoặc trong code controller/service.
