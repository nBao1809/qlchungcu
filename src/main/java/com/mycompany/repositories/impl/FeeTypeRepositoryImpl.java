package com.mycompany.repositories.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.pojo.FeeType;
import com.mycompany.repositories.FeeTypeRepository;

@Repository
@Transactional
public class FeeTypeRepositoryImpl implements FeeTypeRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public FeeType getFeeTypeById(Long id) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            return s.get(FeeType.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<FeeType> getAllFeeTypes() {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            return s.createNamedQuery("FeeType.findAll", FeeType.class)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
