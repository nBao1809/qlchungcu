# API Documentation - QLyChungCu System

## 1. Authentication & User Management

### Public Endpoints
```
[POST] /api/login
Request:
{
    "username": "string",
    "password": "string"
}
Response:
{
    "token": "string" // JWT Token
}
```

### User Endpoints
```
[GET] /api/auth/profile
Header: Authorization Bearer {token}
Response:
{
    "id": number,
    "username": "string",
    "fullName": "string",
    "email": "string",
    "phone": "string",
    "role": "RESIDENT|ADMIN",
    "status": "ACTIVE|INACTIVE"
}

[POST] /api/change-password
Header: Authorization Bearer {token}
Request:
{
    "newPassword": "string"
}
Response: "Đổi mật khẩu thành công"

[POST] /api/update-avatar
Header: Authorization Bearer {token}
Content-Type: multipart/form-data
Request:
- avatar: File
Response: "Cập nhật avatar thành công"
```

### Admin User Management
```
[GET] /api/admin/users
Header: Authorization Bearer {admin_token}
Query params:
- role (optional): "RESIDENT"|"ADMIN"
Response: User[]

[POST] /api/admin/users
Header: Authorization Bearer {admin_token}
Request:
{
    "username": "string",
    "password": "string", 
    "fullName": "string",
    "email": "string",
    "phone": "string",
    "role": "RESIDENT|ADMIN"
}
Response: User

[PUT] /api/admin/users/{userId}
Header: Authorization Bearer {admin_token}
Request:
{
    "fullName": "string",
    "email": "string",
    "phone": "string"
}
Response: User

[PUT] /api/admin/users/{userId}/toggle-status
Header: Authorization Bearer {admin_token}
Response: "Cập nhật trạng thái thành công"
```

## 2. Apartment Management

### Admin Endpoints
```
[GET] /api/admin/apartments
Header: Authorization Bearer {admin_token}
Response: Apartment[]

[POST] /api/admin/apartments
Header: Authorization Bearer {admin_token}
Request:
{
    "apartmentCode": "string",
    "floor": number,
    "block": "string"
}
Response: Apartment

[PUT] /api/admin/apartments/{apartmentId}
Header: Authorization Bearer {admin_token}
Request:
{
    "floor": number,
    "block": "string",
    "status": "string"
}
Response: Apartment

[GET] /api/admin/apartments/{apartmentId}
Header: Authorization Bearer {admin_token}
Response:
{
    "apartment": Apartment,
    "residents": User[]
}

[DELETE] /api/admin/apartments/{apartmentId}
Header: Authorization Bearer {admin_token}
Response: "Xóa căn hộ thành công"
```

## 3. Vehicle Management

### Admin Endpoints
```
[GET] /api/admin/vehicles
Header: Authorization Bearer {admin_token}
Response: Vehicle[]
```

### Resident Endpoints
```
[GET] /api/users/vehicles
Header: Authorization Bearer {token}
Response: Vehicle[]

[POST] /api/users/vehicles
Header: Authorization Bearer {token}
Request:
{
    "licensePlate": "string",
    "vehicleType": "CAR|MOTORBIKE"
}
Response: Vehicle

[GET] /api/users/vehicles/{vehicleId}
Header: Authorization Bearer {token}
Response: Vehicle

[PUT] /api/users/vehicles/{vehicleId}
Header: Authorization Bearer {token}
Request:
{
    "licensePlate": "string",
    "vehicleType": "string"
}
Response: Vehicle
```

## 4. Relative Management

### Admin Endpoints
```
[GET] /api/admin/relatives
Header: Authorization Bearer {admin_token}
Response: Relative[]
```

### Resident Endpoints
```
[GET] /api/users/relatives
Header: Authorization Bearer {token}
Response: Relative[]

[POST] /api/users/relatives
Header: Authorization Bearer {token}
Request:
{
    "fullName": "string",
    "relationship": "string",
    "phone": "string",
    "cccd": "string",
    "hasAccessCard": boolean,
    "hasVehicleCard": boolean
}
Response: Relative

[PUT] /api/users/relatives/{relativeId}
Header: Authorization Bearer {token}
Request:
{
    "fullName": "string",
    "relationship": "string",
    "phone": "string"
}
Response: Relative
```

## 5. Locker Item Management

### Admin Endpoints
```
[GET] /api/admin/locker-items
Header: Authorization Bearer {admin_token}
Response: LockerItem[]

[POST] /api/admin/locker-items
Header: Authorization Bearer {admin_token}
Request:
{
    "itemName": "string",
    "description": "string",
    "residentId": number
}
Response: LockerItem

[PUT] /api/admin/locker-items/{itemId}
Header: Authorization Bearer {admin_token}
Request:
{
    "status": "RECEIVED|DELIVERED"
}
Response: "Cập nhật trạng thái thành công"
```

### Resident Endpoints
```
[GET] /api/users/locker-items
Header: Authorization Bearer {token}
Response: LockerItem[]
```

## 6. Complaint Management

### Admin Endpoints
```
[GET] /api/admin/complaints
Header: Authorization Bearer {admin_token}
Response: [
    {
        "complaintId": number,
        "title": "string",
        "content": "string",
        "imageProof": "string",
        "submittedDate": "string",
        "status": "PENDING|IN_PROGRESS|RESOLVED|REJECTED",
        "resolvedDate": "string",
        "resolutionNote": "string",
        "resident": {
            "id": number,
            "fullName": "string",
            "email": "string",
            "phone": "string"
        }
    }
]

[GET] /api/admin/complaints/{id}
Header: Authorization Bearer {admin_token}
Response: {
    "complaintId": number,
    "title": "string",
    "content": "string",
    "imageProof": "string",
    "submittedDate": "string",
    "status": "PENDING|IN_PROGRESS|RESOLVED|REJECTED",
    "resolvedDate": "string",
    "resolutionNote": "string",
    "resident": {
        "id": number,
        "fullName": "string",
        "email": "string",
        "phone": "string"
    }
}

[PUT] /api/admin/complaints/{id}
Header: Authorization Bearer {admin_token}
Request:
{
    "status": "IN_PROGRESS|RESOLVED|REJECTED",
    "resolutionNote": "string"
}
Response: {
    "message": "Cập nhật trạng thái thành công",
    "complaint": {
        // Complaint object as above
    }
}
```

### Resident Endpoints
```
[GET] /api/users/complaints
Header: Authorization Bearer {token}
Response: [
    {
        "complaintId": number,
        "title": "string",
        "content": "string",
        "imageProof": "string",
        "submittedDate": "string",
        "status": "PENDING|IN_PROGRESS|RESOLVED|REJECTED",
        "resolvedDate": "string",
        "resolutionNote": "string"
    }
]

[GET] /api/users/complaints/{id}
Header: Authorization Bearer {token}
Response: {
    "complaintId": number,
    "title": "string",
    "content": "string",
    "imageProof": "string",
    "submittedDate": "string",
    "status": "PENDING|IN_PROGRESS|RESOLVED|REJECTED",
    "resolvedDate": "string",
    "resolutionNote": "string"
}

[POST] /api/users/complaints
Header: Authorization Bearer {token}
Content-Type: multipart/form-data
Request:
- title: string (required) - Tiêu đề khiếu nại
- content: string (required) - Nội dung chi tiết
- imageProof: File (optional) - Hình ảnh minh chứng
Response: {
    "complaintId": number,
    "title": "string",
    "content": "string",
    "imageProof": "string",
    "submittedDate": "string",
    "status": "PENDING"
}

[GET] /api/users/complaints/{id}
Header: Authorization Bearer {token}
Response: {
    "complaintId": number,
    "title": "string",
    "content": "string",
    "imageProof": "string",
    "submittedDate": "string",
    "status": "string",
    "resolvedDate": "string",
    "resolutionNote": "string"
}
```

## 7. Survey Management

### Admin Endpoints
```
[GET] /api/admin/surveys
Header: Authorization Bearer {admin_token}
Response: Survey[]

[POST] /api/admin/surveys
Header: Authorization Bearer {admin_token}
Request:
{
    "title": "string",
    "description": "string",
    "startDate": "string",
    "endDate": "string",
    "questions": [
        {
            "questionText": "string",
            "questionType": "SINGLE_CHOICE|RATING|TEXT",
            "required": boolean,
            "options": ["string"] // For SINGLE_CHOICE only
        }
    ]
}
Response: Survey

[PUT] /api/admin/surveys/{surveyId}/status
Header: Authorization Bearer {admin_token}
Request:
{
    "status": "ACTIVE|INACTIVE|CLOSED"
}
Response: Survey

[GET] /api/admin/surveys/{surveyId}
Header: Authorization Bearer {admin_token}
Response: Survey

[GET] /api/admin/surveys/{surveyId}/results
Header: Authorization Bearer {admin_token}
Response:
{
    "survey": Survey,
    "results": SurveyResult[]
}
```

### Resident Endpoints
```
[GET] /api/users/surveys
Header: Authorization Bearer {token}
Response: Survey[]

[GET] /api/users/surveys/{surveyId}
Header: Authorization Bearer {token}
Response: Survey

[POST] /api/users/surveys/{surveyId}/respond
Header: Authorization Bearer {token}
Request:
{
    "answers": [
        {
            "questionId": number,
            "optionId": number,      // For SINGLE_CHOICE
            "textAnswer": "string",   // For TEXT
            "ratingValue": number     // For RATING
        }
    ]
}
Response: "Gửi câu trả lời thành công"
```

## 8. Bill Management

### Admin Endpoints
```
[GET] /api/admin/bills
Header: Authorization Bearer {admin_token}
Response: Bill[]

[POST] /api/admin/bills
Header: Authorization Bearer {admin_token}
Request:
{
    "apartmentId": number,
    "month": number,
    "year": number,
    "details": [
        {
            "feeTypeId": number,
            "quantity": number,
            "unitPrice": number,
            "note": "string"
        }
    ]
}
Response: Bill

[GET] /api/admin/bills/{billId}
Header: Authorization Bearer {admin_token}
Response: Bill

[GET] /api/admin/bills/{billId}/details
Header: Authorization Bearer {admin_token}
Response:
{
    "bill": Bill,
    "details": BillDetail[]
}

[PUT] /api/admin/bills/{billId}/confirm-payment
Header: Authorization Bearer {admin_token}
Request:
{
    "status": "CONFIRMED|REJECTED"
}
Response: "Cập nhật trạng thái thanh toán thành công"

[GET] /api/admin/bills/fee-types
Header: Authorization Bearer {admin_token}
Response: FeeType[]
```

### Resident Endpoints
```
[GET] /api/users/bills
Header: Authorization Bearer {token}
Response: Bill[]

[GET] /api/users/bills/{billId}
Header: Authorization Bearer {token}
Response: Bill

[GET] /api/users/bills/{billId}/details
Header: Authorization Bearer {token}
Response:
{
    "bill": Bill,
    "details": BillDetail[]
}

[POST] /api/users/bills/{billId}/payment-proof
Header: Authorization Bearer {token}
Content-Type: multipart/form-data
Request:
- file: File
Response: Bill

[POST] /api/users/bills/{billId}/online-payment
Header: Authorization Bearer {token}
Request:
{
    "paymentMethod": "VNPAY|MOMO|ZALOPAY"
}
Response: PaymentTransaction
```

## Error Responses
Tất cả các API đều có thể trả về các mã lỗi HTTP phù hợp:
```
400 Bad Request - Dữ liệu không hợp lệ
401 Unauthorized - Chưa đăng nhập
403 Forbidden - Không có quyền truy cập  
404 Not Found - Không tìm thấy tài nguyên
500 Internal Server Error - Lỗi server
```
