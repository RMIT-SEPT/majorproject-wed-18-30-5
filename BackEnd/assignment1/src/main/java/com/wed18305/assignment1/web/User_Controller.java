package com.wed18305.assignment1.web;

import java.util.Optional;

import javax.validation.Valid;

import com.wed18305.assignment1.model.Response;
import com.wed18305.assignment1.model.User;
import com.wed18305.assignment1.services.User_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class User_Controller {

    @Autowired
    private User_Service userService;

    /**
     * Create new user 
     * POST ENDPOINT: http://localhost:8080/api/user 
     * INPUT JSON {"name":"neil", 
     *             "username":"neil", 
     *             "password":"1234",
     *             "contactNumber":"0425000000"}
     * UserType NOT CURRENTLY IMPLEMENTED
     */
    @PostMapping()
    public ResponseEntity<Response> createNewUser(@Valid @RequestBody User user, BindingResult result) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Save new User
        User user1 = null;
        try {
            user1 = userService.saveOrUpdateUser(user);
        } catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Check user result
        if (user1 == null) {
            Response response = new Response(false, "ERROR!", "user returned NULL", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "user created!", null, user1);
            return new ResponseEntity<Response>(response, HttpStatus.CREATED);
        }
    }

    /**
     * Get user via username and password
     * GET ENDPOINT: http://localhost:8080/api/user
     * INPUT JSON{"name":"neil", 
     *            "password":"1234"} 
     */
    @GetMapping()
    public ResponseEntity<Response> findByUsernameAndPassword(@Valid @RequestBody User user, BindingResult result) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Find the user
        Optional<User> user1 = null;
        try {
            user1 = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            Response response = new Response(false,"ERROR!",e.getMessage(),null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        //Check the user result
        if(!user1.isPresent()){
            Response response = new Response(false,"user not found!",null,null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }else{
            Response response = new Response(true,"user found!",null,user1.get());
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
    }
}