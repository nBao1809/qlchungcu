/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.pojo.Relative;
import com.mycompany.pojo.User;
import com.mycompany.repositories.RelativeRepository;
import com.mycompany.services.RelativeService;

/**
 *
 * @author baoto
 */
@Service
public class RelativeServiceImpl implements RelativeService {

    @Autowired
    private RelativeRepository relativeRepo;

    @Override
    public List<Relative> getAllRelatives() {
        return relativeRepo.getAllRelatives();
    }

    @Override
    public List<Relative> getRelativesByResident(User user) {
        return relativeRepo.getRelativesByResident(user);
    }

    @Override
    public Relative addRelative(User user, Map<String, String> params) {
        Relative r = new Relative();
        r.setFullName(params.get("fullName"));
        r.setRelationship(params.get("relationship"));
        r.setPhone(params.get("phone")); 
        r.setCccd(params.get("cccd"));
        r.setResidentId(user);
        
        r.setHasAccessCard(params.containsKey("hasAccessCard") ? 
            Boolean.valueOf(params.get("hasAccessCard")) : false);
        r.setHasVehicleCard(params.containsKey("hasVehicleCard") ? 
            Boolean.valueOf(params.get("hasVehicleCard")) : false);
        
        Date now = new Date();
        r.setCreatedAt(now);
        r.setUpdatedAt(now);
        r.setStatus(true);

        return relativeRepo.addRelative(r);
    }

    @Override
    public Relative updateRelative(Long relativeId, Map<String, String> params) {
        Relative r = relativeRepo.getRelativeById(relativeId);
        if (r == null) {
            return null;
        }

        if (params.containsKey("fullName")) {
            r.setFullName(params.get("fullName"));
        }
        if (params.containsKey("relationship")) {
            r.setRelationship(params.get("relationship"));
        }
        if (params.containsKey("phone")) {
            r.setPhone(params.get("phone"));
        }
        if (params.containsKey("cccd")) {
            r.setCccd(params.get("cccd"));
        }
        if (params.containsKey("hasAccessCard")) {
            r.setHasAccessCard(Boolean.valueOf(params.get("hasAccessCard")));
        }
        if (params.containsKey("hasVehicleCard")) {
            r.setHasVehicleCard(Boolean.valueOf(params.get("hasVehicleCard")));
        }
        if (params.containsKey("status")) {
            r.setStatus(Boolean.valueOf(params.get("status")));
        }

        r.setUpdatedAt(new Date());
        relativeRepo.updateRelative(r);
        return r;
    }

    @Override
    public Relative getRelativeById(Long relativeId) {
        try {
            return relativeRepo.getRelativeById(relativeId);
        } catch (Exception e) {
            return null;
        }
    }
}
