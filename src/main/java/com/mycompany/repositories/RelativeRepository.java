/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.repositories;
/**
 *
 * @author baoto
 */
import java.util.List;

import com.mycompany.pojo.Relative;
import com.mycompany.pojo.User;

public interface RelativeRepository {
    List<Relative> getAllRelatives();
    List<Relative> getRelativesByResident(User user);
    Relative addRelative(Relative r);
    void updateRelative(Relative r);
    Relative getRelativeById(Long id);
}
