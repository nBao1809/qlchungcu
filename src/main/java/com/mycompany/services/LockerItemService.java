/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.services;

import java.util.List;
import java.util.Map;

import com.mycompany.pojo.LockerItem;
import com.mycompany.pojo.User;

/**
 *
 * @author baoto
 */
public interface LockerItemService {
    List<LockerItem> getAllLockerItems();
    LockerItem addLockerItem(Map<String, String> params, User admin);
    void updateLockerItemStatus(Long itemId, String status, User receiver);
    List<LockerItem> getLockerItemsByResident(User resident);
}
