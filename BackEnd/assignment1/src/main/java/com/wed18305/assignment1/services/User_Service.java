package com.wed18305.assignment1.services;

import java.util.Optional;

import com.wed18305.assignment1.model.User;
import com.wed18305.assignment1.repositories.User_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class User_Service {
    @Autowired
    private User_Repository userRepository;

    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
}