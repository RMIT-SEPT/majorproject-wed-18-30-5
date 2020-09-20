package com.wed18305.assignment1.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;
import javax.validation.Valid;
import com.wed18305.assignment1.Responses.Response;
import com.wed18305.assignment1.Requests.AddService_Request;
import com.wed18305.assignment1.Requests.Delete_Request;
import com.wed18305.assignment1.Requests.UpdateDetails_Request;
import com.wed18305.assignment1.Requests.UserService_Request;
import com.wed18305.assignment1.Requests.User_Request;
import com.wed18305.assignment1.model.Entity_Service;
import com.wed18305.assignment1.model.Entity_User;
import com.wed18305.assignment1.services.Service_Service;
import com.wed18305.assignment1.services.UserType_Service;
import com.wed18305.assignment1.services.User_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class User_Controller {

    @Autowired
    private User_Service userService;
    @Autowired
    private UserType_Service userTypeService;
    @Autowired
    private Service_Service userServiceService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Create new (customer)user 
     * <p>
     * POST ENDPOINT: http://localhost:8080/api/user/create
     * <p>
     * INPUT JSON {"name":"neil", 
     *             "username":"neil", 
     *             "password":"1234",
     *             "contactNumber":"0425000000"}
     * @param ur
     * @param result
     * @return Response object, if successfull the user is returned in the body
     * otherwise the error object will contain either a single string or array of field errors 
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
        Entity_User user1 = null;
        try {
            //Create a User entity using the Customer_Request
            Entity_User user = new Entity_User(ur.getName(),
                                            ur.getUsername(),
                                            passwordEncoder.encode(ur.getPassword()),
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
     * Create new (employee)user, *only admins can call this endpoint
     * <p>
     * POST ENDPOINT: http://localhost:8080/api/user/createEmployee
     * <p>
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
        Entity_User user1 = null;
        try {
             //Create a User entity using the Employee_Request
             Entity_User user = new Entity_User(ur.getName(),
                                            ur.getUsername(),
                                            passwordEncoder.encode(ur.getPassword()),
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
     * Create new (admin)user, *only admins can call this endpoint
     * <p>
     * POST ENDPOINT: http://localhost:8080/api/user/createEmployee
     * <p>
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

        Entity_User user1 = null;
        // Save new User
        try {
             //Create a User entity using the Admin_Request
             Entity_User user = new Entity_User(ur.getName(),
                                                ur.getUsername(),
                                                passwordEncoder.encode(ur.getPassword()),
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
     * Get all employees, *only admins can call this endpoint
     * <p>
     * GET ENDPOINT: http://localhost:8080/api/user/getEmployees
     * <p>
     * @param ur
     * @param result
     * @return Response object, if successfull the employees are returned in the body
     * otherwise the error object will contain either a single string or array of field errors 
     */
    @GetMapping("getEmployees")
    public ResponseEntity<Response> getEmployees() {
        // Get any employees
        Iterable<Entity_User> employees = null;
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
     * Get all employees by service *only registered users can call this endpoint
     * <p>
     * POST ENDPOINT: http://localhost:8080/api/user/getEmployeesByService
     * <p>
     * @param ur
     * @param result
     * @return Response object, if successfull the employees are returned in the body
     * otherwise the error object will contain either a single string or array of field errors 
     */
    @PostMapping("getEmployeesByService")
    public ResponseEntity<Response> getEmployeesByService(@Valid @RequestBody UserService_Request usr, BindingResult result) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        // Make sure the service exists in the DB
        Optional<Entity_Service> s = userServiceService.findById(usr.getServiceID());
        if(!s.isPresent()){
            //shouldn't be able to get here but just incase
            Response response = new Response(false, "ERROR!", "No service found!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        // Get any employees with the service id
        Iterable<Entity_User> employees = null;
        ArrayList<Entity_User> emplWithService = new ArrayList<Entity_User>();
        try {
            employees = userService.findAllByTypeId((long) 2);
            for (Entity_User employee : employees) {
                if(employee.getServices().contains(s.get())){
                    emplWithService.add(employee);
                }
            }
        } catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Check employees result
        // if (emplWithService.size() == 0) {
        //     Response response = new Response(true, "ERROR!", "No employees with service "+s.get().getName(), null, emplWithService);
        //     return new ResponseEntity<Response>(response, HttpStatus.OK);
        // } else {
            Response response = new Response(true, "Maybe some employees found?", null, emplWithService);
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        // }
    }


    /**
     * Update user details
     * <p>
     * POST ENDPOINT: http://localhost:8080/api/user/updateUser
     * <p>
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
        Optional<Entity_User> user = userService.findByUsername(p.getName());
        if(user.isPresent() == false){
            //shouldn't be able to get here but just incase
            Response response = new Response(false, "ERROR!", "No user to update!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        //Update the user
        Entity_User user1 = null;
        Entity_User currentUser = user.get();
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
     * <p>
     * POST ENDPOINT: http://localhost:8080/api/user/deleteCustomer
     * <p>
     * @param ur
     * @param result
     * @return Response object, if successfull the body will be null
     * otherwise the error object will contain a single string
     */
    @PostMapping("deleteCustomer")
    public ResponseEntity<Response> deleteCustomer(Principal p) {
        // Make sure the logged in user exists
        Optional<Entity_User> user = userService.findByUsername(p.getName());
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
           Response response = new Response(false, "ERROR!", "Unable to delete user "+e.getMessage(), null);
           return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        //TODO response should be a redirect to logut function.
        Response response = new Response(true, "User has been deleted!", null, null);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    /**
     * Delete User *only admins can call this endpoint
     * <p>
     * POST ENDPOINT: http://localhost:8080/api/user/deleteUser
     * <p>
     * @param dr
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

        Long[] userIds = dr.getLong();
        if(userIds == null){
            Response response = new Response(false, "ERROR!", "Unable to get users!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        
        //Delete the user(s)
        try {
            userService.deleteAll(userIds);  
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

    /**
     * Add Service *only admins can call this endpoint
     * <p>
     * POST ENDPOINT: http://localhost:8080/api/user/addService
     * <p>
     * @param ur
     * @param result
     * @return Response object, if successfull the body will be null
     * otherwise the error object will contain either a single string or array of field errors 
     */
    @PostMapping("addService")
    public ResponseEntity<Response> addService(@Valid @RequestBody AddService_Request sr, BindingResult result) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        // Make sure the service(s) already exist
        ArrayList<Long> services = new ArrayList<Long>();
        for (Long id : sr.getServiceIds()) {
            services.add(id);
        }
        ArrayList<Entity_Service> es = (ArrayList<Entity_Service>) userServiceService.findAllByIds(services);
        if(es.isEmpty()){
            Response response = new Response(false, "ERROR!", "No service(s) found!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        // Make sure the users exist and are employees or admins 
        ArrayList<Entity_User> eu = (ArrayList<Entity_User>) userService.findEmployeesById(sr.getUserIds());
        if(eu.isEmpty()){
            Response response = new Response(false, "ERROR!", "No user(s) found!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //Add the service to the user
        try {
            userService.addServicesToEmployees(eu, es);
        } catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //Success
        Response response = new Response(true, "Services added!", null, null);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}