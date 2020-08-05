package com.wed18305.assignment1.web;

import com.wed18305.assignment1.model.User;
import com.wed18305.assignment1.services.User_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class User_Controller{

    @Autowired
    private User_Service userService;

    @PostMapping("")
    public ResponseEntity<User> createNewUser(@RequestBody User user){
        User user1 = userService.saveOrUpdateUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
}