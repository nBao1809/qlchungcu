/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repositories.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.pojo.User;
import com.mycompany.repositories.UserRepository;

/**
 *
 * @author admin
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query q = s.createNamedQuery("User.findByUsername", User.class);
            q.setParameter("username", username);
            return (User) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User addUser(User u) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.persist(u);
            return u;
        } catch (Exception e) {
            return null;
        }
    }    @Override
    public boolean authenticate(String username, String password) {
        try {
            User u = this.getUserByUsername(username);
            if (u != null && "ACTIVE".equals(u.getStatus())) {
                return this.passwordEncoder.matches(password, u.getPassword());
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }    @Override
    public void updateUser(User u) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            User existingUser = getUserById(u.getId());
            if (existingUser != null && !existingUser.getPassword().equals(u.getPassword())) {
                // Nếu mật khẩu thay đổi, đặt hasChangePassword = true
                u.setPasswordChanged(true);
            }
            s.update(u);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    @Override
    public List<User> getUsersByRole(String role) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query q;
            if (role != null && !role.isEmpty())
                q = s.createNamedQuery("User.findByRole", User.class).setParameter("role", role);
            else
                q = s.createQuery("FROM User", User.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User getUserById(Long id) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query q = s.createNamedQuery("User.findById", User.class);
            q.setParameter("id", id);
            return (User) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
