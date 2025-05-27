/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.repositories;

import java.util.List;

import com.mycompany.pojo.LockerItem;
import com.mycompany.pojo.User;

/**
 *
 * @author baoto
 */
public interface LockerItemRepository {
    List<LockerItem> getAllLockerItems();
    LockerItem addLockerItem(LockerItem item);
    void updateLockerItem(LockerItem item);
    LockerItem getLockerItemById(Long itemId);
    List<LockerItem> getLockerItemsByResident(User resident);
}
