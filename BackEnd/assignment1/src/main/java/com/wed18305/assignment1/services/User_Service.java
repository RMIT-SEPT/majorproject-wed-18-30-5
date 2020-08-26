package com.wed18305.assignment1.services;

import java.util.ArrayList;
import java.util.List;
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

    public List<User_model> findManyById(Long[] ids){
        
        List<User_model> users = new ArrayList<User_model>();

        for (int i = 0; i < ids.length; i++) {
            users.add(userRepository.findById(ids[i]).get());
        }

        return users;
    }
    //Employees are users that are either employees or admins
    public List<User_model> findManyEmployeesById(Long[] ids){   
        List<User_model> users = new ArrayList<User_model>();
        for (int i = 0; i < ids.length; i++) {
            User_model u = userRepository.findById(ids[i]).get();
            if(u.getType().getId() == 1 || u.getType().getId() == 2){
                users.add(u);
            }
        }
        return users;
    }

    public Iterable<User_model> findAllByTypeId(Long id){
        return userRepository.findAllByUserTypeId(id);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public void deleteAll(Iterable<User_model> users){
        userRepository.deleteAll(users);
    }
}