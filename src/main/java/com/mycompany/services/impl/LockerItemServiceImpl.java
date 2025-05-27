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

import com.mycompany.pojo.LockerItem;
import com.mycompany.pojo.User;
import com.mycompany.repositories.LockerItemRepository;
import com.mycompany.repositories.UserRepository;
import com.mycompany.services.LockerItemService;

/**
 *
 * @author baoto
 */
@Service
public class LockerItemServiceImpl implements LockerItemService {
    @Autowired
    private LockerItemRepository lockerItemRepo;
    @Autowired
    private UserRepository userRepo;

    @Override
    public List<LockerItem> getAllLockerItems() {
        return lockerItemRepo.getAllLockerItems();
    }

    @Override
    public LockerItem addLockerItem(Map<String, String> params, User admin) {
        LockerItem item = new LockerItem();
        item.setItemName(params.get("itemName"));
        item.setDescription(params.get("description"));
        
        // Set timestamps
        Date now = new Date();
        item.setReceivedDate(now);
        item.setDeliveredDate(now);
        
        item.setStatus("RECEIVED");
        item.setNote(params.get("note"));
        item.setReceivedByAdminId(admin);
        if (params.containsKey("residentId")) {
            User resident = userRepo.getUserById(Long.parseLong(params.get("residentId")));
            item.setResidentId(resident);
        }
        return lockerItemRepo.addLockerItem(item);
    }

    @Override
    public void updateLockerItemStatus(Long itemId, String status, User receiver) {
        LockerItem item = lockerItemRepo.getLockerItemById(itemId);
        if (item != null) {
            item.setStatus(status);
            if ("DELIVERED".equals(status)) {
                item.setDeliveredDate(new Date());
            }
            item.setReceivedByAdminId(receiver);
            
            // Update timestamp
            item.setReceivedDate(new Date());
            
            lockerItemRepo.updateLockerItem(item);
        }
    }

    @Override
    public List<LockerItem> getLockerItemsByResident(User resident) {
        return lockerItemRepo.getLockerItemsByResident(resident);
    }
}
