package com.wed18305.assignment1.services;

import com.wed18305.assignment1.model.User;
import com.wed18305.assignment1.repositories.User_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class User_Service {
    @Autowired
    private User_Repository userRepository;

    public User saveOrUpdateUser(User user){
        return userRepository.save(user);
    }

    public User findByNameAndPassword(String name, String password){
        return userRepository.findByNameAndPassword(name, password);
    }
}