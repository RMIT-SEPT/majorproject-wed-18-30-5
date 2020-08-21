package com.wed18305.assignment1.web;

import javax.validation.Valid;
import com.wed18305.assignment1.Responses.Response;
import com.wed18305.assignment1.Requests.User_Request;
import com.wed18305.assignment1.model.User_model;
import com.wed18305.assignment1.services.UserType_Service;
import com.wed18305.assignment1.services.User_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class User_Controller {

    @Autowired
    private User_Service userService;
    @Autowired
    private UserType_Service userTypeService;

    /**
     * Create new (customer)user 
     * POST ENDPOINT: http://localhost:8080/api/user/create
     * INPUT JSON {"name":"neil", 
     *             "username":"neil", 
     *             "password":"1234",
     *             "contactNumber":"0425000000"}
     */
    @PostMapping("createCustomer")
    public ResponseEntity<Response> createNewUser(@Valid @RequestBody User_Request ur, BindingResult result) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Save new User
        User_model user1 = null;
        try {
            //Create a User entity using the Customer_Request
            User_model user = new User_model(ur.getName(),
                                            ur.getUsername(),
                                            ur.getPassword(),
                                            ur.getContactNumber().toString(),
                                            userTypeService.findById((long)3).get());
            //Save user
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
     * Create new (employee)user, requires a requestID(userID) that is attempting to create the new employee
     * POST ENDPOINT: http://localhost:8080/api/user/createEmployee
     * INPUT JSON {"name":"neil", 
     *             "username":"neilk", 
     *             "password":"1234",
     *             "contactNumber":"0425000000"}
     * @param employee
     * @param result
     * @return Response object, if successfull the user is returned in the body
     * otherwise the error object will contain either a single string or array of field errors 
     */
    @PostMapping("createEmployee")
    public ResponseEntity<Response> createNewEmployeeUser(@Valid @RequestBody User_Request ur, BindingResult result) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Save new User
        User_model user1 = null;
        try {
             //Create a User entity using the Employee_Request
             User_model user = new User_model(ur.getName(),
                                            ur.getUsername(),
                                            ur.getPassword(),
                                            ur.getContactNumber().toString(),
                                            userTypeService.findById((long)2).get());
            //Save user
            user1 = userService.saveOrUpdateUser(user);
        } catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Check user result
        if (user1 == null) {
            Response response = new Response(false, "ERROR!", "User not created!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "User created!", null, user1);
            return new ResponseEntity<Response>(response, HttpStatus.CREATED);
        }
    }




    /**
     * Create new (admin)user, requires a requestID(Admin) that is attempting to create the new employee
     * POST ENDPOINT: http://localhost:8080/api/user/createEmployee
     * INPUT JSON {"name":"neil", 
     *             "username":"neilk", 
     *             "password":"1234",
     *             "contactNumber":"0425000000"}
     * @param admin
     * @param result
     * @return Response object, if successfull the user is returned in the body
     * otherwise the error object will contain either a single string or array of field errors 
     */
    @PostMapping("createAdmin")
    public ResponseEntity<Response> createNewAdminUser(@Valid @RequestBody User_Request ur, BindingResult result) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        User_model user1 = null;
        // Save new User
        try {
             //Create a User entity using the Admin_Request
             User_model user = new User_model(ur.getName(),
                                                ur.getUsername(),
                                                ur.getPassword(),
                                                ur.getContactNumber().toString(),
                                                userTypeService.findById((long)1).get());
            //Save user
            user1 = userService.saveOrUpdateUser(user);
        } catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Check user result
        if (user1 == null) {
            Response response = new Response(false, "ERROR!", "User not created!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "User created!", null, user1);
            return new ResponseEntity<Response>(response, HttpStatus.CREATED);
        }
    }
}