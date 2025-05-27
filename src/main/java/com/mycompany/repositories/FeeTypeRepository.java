package com.mycompany.repositories;

import java.util.List;

import com.mycompany.pojo.FeeType;

public interface FeeTypeRepository {
    FeeType getFeeTypeById(Long id);
    List<FeeType> getAllFeeTypes();
}
