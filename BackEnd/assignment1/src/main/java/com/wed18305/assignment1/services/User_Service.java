package com.wed18305.assignment1.services;

import java.util.Optional;

import com.wed18305.assignment1.model.User_model;
import com.wed18305.assignment1.repositories.User_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class User_Service {
    @Autowired
    private User_Repository userRepository;

    public User_model saveOrUpdateUser(User_model user) {
        return userRepository.save(user);
    }

    public Optional<User_model> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User_model> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public Optional<User_model> findById(Long id){
        return userRepository.findById(id);
    }
}