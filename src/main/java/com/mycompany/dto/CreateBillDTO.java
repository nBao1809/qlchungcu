package com.mycompany.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateBillDTO {
    @JsonProperty("apartment_id")
    private Long apartmentId;
    private Short month;
    private Integer year;    @JsonProperty("bill_details")
    private List<BillDetailDTO> billDetails;

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Short getMonth() {
        return month;
    }

    public void setMonth(Short month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }    public List<BillDetailDTO> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(List<BillDetailDTO> billDetails) {
        this.billDetails = billDetails;
    }
}
