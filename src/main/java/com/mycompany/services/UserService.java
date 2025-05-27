/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.pojo.User;

/**
 *
 * @author admin
 */
public interface UserService extends UserDetailsService {

    User getUserByUsername(String username);

    User addUser(Map<String, String> params, MultipartFile avatar);

    boolean authenticate(String username, String password);

    void changePassword(User user, String newPassword);

    void updateAvatar(User user, MultipartFile avatar);

    List<User> getUsersByRole(String role);

    User updateUserInfo(Long userId, Map<String, String> params);

    boolean toggleUserStatus(Long userId);
}
