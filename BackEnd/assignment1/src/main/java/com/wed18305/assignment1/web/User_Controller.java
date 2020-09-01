package com.wed18305.assignment1.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import com.wed18305.assignment1.Responses.Response;
import com.wed18305.assignment1.Requests.Delete_Request;
import com.wed18305.assignment1.Requests.UpdateDetails_Request;
import com.wed18305.assignment1.Requests.User_Request;
import com.wed18305.assignment1.model.User_model;
import com.wed18305.assignment1.services.UserType_Service;
import com.wed18305.assignment1.services.User_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @CrossOrigin(origins = "http://localhost:3000")
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
     * Create new (employee)user, an admin must be logged in to enable this request.
     * POST ENDPOINT: http://localhost:8080/api/user/createEmployee
     * INPUT JSON {"name":"neil", 
     *             "username":"neilk", 
     *             "password":"1234",
     *             "contactNumber":"0425000000"}
     * @param ur
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
     * Create new (admin)user, an admin must be logged in to enable this request.
     * POST ENDPOINT: http://localhost:8080/api/user/createEmployee
     * INPUT JSON {"name":"neil", 
     *             "username":"neilk", 
     *             "password":"1234",
     *             "contactNumber":"0425000000"}
     * @param ur
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

    /**
     * Get all employees
     * GET ENDPOINT: http://localhost:8080/api/user/getEmployees
     * @param ur
     * @param result
     * @return Response object, if successfull the employees are returned in the body
     * otherwise the error object will contain either a single string or array of field errors 
     */
    @GetMapping("getEmployees")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Response> getEmployees(Principal p) {
        // Get any employees
        Iterable<User_model> employees = null;
        try {
            employees = userService.findAllByTypeId((long) 2);
        } catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Check employees result
        if (employees == null) {
            Response response = new Response(false, "ERROR!", "No employees!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "Employees found!", null, employees);
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
    }

    /**
     * Update user details
     * POST ENDPOINT: http://localhost:8080/api/user/updateUser
     * @param ur
     * @param result
     * @return Response object, if successfull the updated user is returned in the body
     * otherwise the error object will contain either a single string or array of field errors 
     */
    @PostMapping("updateUser")
    public ResponseEntity<Response> updateUserDetails(@Valid @RequestBody UpdateDetails_Request udr, BindingResult result, Principal p) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Make sure the logged in user exists
        Optional<User_model> user = userService.findByUsername(p.getName());
        if(user.isPresent() == false){
            //shouldn't be able to get here but just incase
            Response response = new Response(false, "ERROR!", "No user to update!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        //Update the user
        User_model user1 = null;
        User_model currentUser = user.get();
        currentUser.setName(udr.getName());
        currentUser.setUsername(udr.getUsername());
        currentUser.setContactNumber(udr.getContactNumber().toString());
        try {
            user1 = userService.saveOrUpdateUser(currentUser);
        } catch (Exception e) {
           Response response = new Response(false, "ERROR!", "Username already in use", null);
           return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Check employees result
        if (user1 == null) {
            Response response = new Response(false, "ERROR!", "Update failed!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "User details updated!", null, user1);
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
    }

    //TODO add a updateUserPassword - seperate this from updateUserDetails
    /**
     * Delete Customer *only customers can call this endpoint
     * POST ENDPOINT: http://localhost:8080/api/user/deleteCustomer
     * @param ur
     * @param result
     * @return Response object, if successfull the body will be null
     * otherwise the error object will contain a single string
     */
    @PostMapping("deleteCustomer")
    public ResponseEntity<Response> deleteCustomer(Principal p) {
        // Make sure the logged in user exists
        Optional<User_model> user = userService.findByUsername(p.getName());
        if(user.isPresent() == false){
            //shouldn't be able to get here but just incase
            Response response = new Response(false, "ERROR!", "User doesn't exist!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        //Delete the user
        try {
            userService.deleteById(user.get().getId());   
        }catch (IllegalArgumentException e) {
            Response response = new Response(false, "ERROR!", "Id of user is null", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
           Response response = new Response(false, "ERROR!", "Unable to delete user", null);
           return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        //TODO response should be a redirect to logut function.
        Response response = new Response(true, "User has been deleted!", null, null);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    /**
     * Delete User *only admins can call this endpoint
     * POST ENDPOINT: http://localhost:8080/api/user/deleteUser
     * @param ur
     * @param result
     * @return Response object, if successfull the body will be null
     * otherwise the error object will contain either a single string or array of field errors 
     */
    @PostMapping("deleteUser")
    public ResponseEntity<Response> deleteUser(@Valid @RequestBody Delete_Request dr, BindingResult result) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        List<User_model> users = new ArrayList<User_model>();
        String[] subStrings = dr.getUsername();
        // Make sure the user(s) we want to delete exists
        if(subStrings != null){
            if(subStrings.length > 0){
                for (String i : subStrings) {
                    try{
                        Optional<User_model> um = userService.findByUsername(i);
                        if(um.isPresent()){
                            users.add(um.get());
                        } 
                    }catch(Exception e){
                        //shouldn't be able to get here but just incase
                        Response response = new Response(false, "ERROR!", "Unable to get users!", null);
                        return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
                    }
                }
            }
        }
        
        //Delete the user(s)
        try {
            userService.deleteAll(users);  
        }catch (IllegalArgumentException e) {
            Response response = new Response(false, "ERROR!", "Id of user is null", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
           Response response = new Response(false, "ERROR!", "Unable to delete user", null);
           return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        
        Response response = new Response(true, "User(s) deleted!", null, null);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}