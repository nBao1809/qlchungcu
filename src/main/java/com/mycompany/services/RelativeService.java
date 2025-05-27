/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.services;

import java.util.List;
import java.util.Map;

import com.mycompany.pojo.Relative;
import com.mycompany.pojo.User;

/**
 *
 * @author baoto
 */
public interface  RelativeService {

    public List<Relative> getAllRelatives();

    public List<Relative> getRelativesByResident(User user);

    public Relative addRelative(User user, Map<String, String> params);

    public Relative updateRelative(Long relativeId, Map<String, String> params);
}
