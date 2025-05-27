package com.mycompany.repositories;

import java.util.List;

import com.mycompany.pojo.BillDetail;

public interface BillDetailRepository {
    BillDetail addBillDetail(BillDetail detail);
    List<BillDetail> getBillDetailsByBillId(Long billId);
}
