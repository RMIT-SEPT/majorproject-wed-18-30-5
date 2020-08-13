package com.wed18305.assignment1.web;

import java.util.Optional;

import javax.validation.Valid;

import com.wed18305.assignment1.Responses.Response;
import com.wed18305.assignment1.Requests.Admin_Request;
import com.wed18305.assignment1.Requests.Employee_Request;
import com.wed18305.assignment1.Requests.Login_Request;
import com.wed18305.assignment1.Requests.Customer_Request;
import com.wed18305.assignment1.model.User;
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
    public ResponseEntity<Response> createNewUser(@Valid @RequestBody Customer_Request cr, BindingResult result) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Save new User
        User user1 = null;
        try {
            //Create a User entity using the Customer_Request
            User user = new User(cr.getName(),
                                 cr.getUsername(),
                                 cr.getPassword(),
                                 cr.getContactNumber().toString(),
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
     *             "contactNumber":"0425000000",
     *             "requestID":"111"}
     * @param employee
     * @param result
     * @return Response object, if successfull the user is returned in the body
     * otherwise the error object will contain either a single string or array of field errors 
     */
    @PostMapping("createEmployee")
    public ResponseEntity<Response> createNewEmployeeUser(@Valid @RequestBody Employee_Request er, BindingResult result) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        //Check that the requestID is an admin
        Optional<User> adminRequest = userService.findById(er.getRequestID());
        if(!adminRequest.isPresent()){
            //No user found with that ID
            Response response = new Response(false, "ERROR!", "Not a valid request!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }else{
            if(adminRequest.get().getType().getId().intValue() != 1){
                //Not a valid admin user request
                Response response = new Response(false, "ERROR!", "Not a valid request!", null);
                return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
            }
        }

        // Save new User
        User user1 = null;
        try {
             //Create a User entity using the Employee_Request
             User user = new User(er.getName(),
                                  er.getUsername(),
                                  er.getPassword(),
                                  er.getContactNumber().toString(),
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
     *             "contactNumber":"0425000000",
     *             "requestID":"111"}
     * @param admin
     * @param result
     * @return Response object, if successfull the user is returned in the body
     * otherwise the error object will contain either a single string or array of field errors 
     */
    @PostMapping("createAdmin")
    public ResponseEntity<Response> createNewAdminUser(@Valid @RequestBody Admin_Request ar, BindingResult result) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        //Check that the requestID is an admin(only an admin can create an admin)
        Optional<User> adminRequest = userService.findById(ar.getRequestID());
        if(!adminRequest.isPresent()){
            //No user found with that ID
            Response response = new Response(false, "ERROR!", "Not a valid request!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }else{
            if(adminRequest.get().getType().getId().intValue() != 1){
                //Not a valid admin user request
                Response response = new Response(false, "ERROR!", "Not a valid request!", null);
                return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
            }
        }

        User user1 = null;
        // Save new User
        try {
             //Create a User entity using the Admin_Request
             User user = new User(ar.getName(),
                                  ar.getUsername(),
                                  ar.getPassword(),
                                  ar.getContactNumber().toString(),
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
     * Get user via username and password
     * GET ENDPOINT: http://localhost:8080/api/user/find
     * INPUT JSON{"name":"neil", 
     *            "password":"1234"} 
     */
    @PostMapping("Login")
    public ResponseEntity<Response> findByUsernameAndPassword(@Valid @RequestBody Login_Request lr, BindingResult result) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Find the user
        Optional<User> user1 = null;
        try {
            user1 = userService.findByUsernameAndPassword(lr.getUsername(), lr.getPassword());
        } catch (Exception e) {
            Response response = new Response(false,"ERROR!",e.getMessage(),null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        //Check the user result
        if(!user1.isPresent()){
            Response response = new Response(false,"ERROR!","user not found!",null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }else{
            Response response = new Response(true,"user found!",null,user1.get());
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
    }
}