/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repositories;

import java.util.List;

import com.mycompany.pojo.User;

/**
 *
 * @author baoto
 */
    public interface UserRepository {
    User getUserByUsername(String username);
    User addUser(User u);
    boolean authenticate(String username, String password);
    void updateUser(User user);
    List<User> getUsersByRole(String role);
    User getUserById(Long id);
}
