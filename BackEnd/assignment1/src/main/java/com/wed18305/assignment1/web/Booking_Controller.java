package com.wed18305.assignment1.web;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import com.wed18305.assignment1.Responses.Response;
import com.wed18305.assignment1.Requests.Booking_Request;
import com.wed18305.assignment1.model.Booking;
import com.wed18305.assignment1.services.Booking_Service;
import com.wed18305.assignment1.services.Service_Service;
import com.wed18305.assignment1.model.User;
import com.wed18305.assignment1.model.UserType;
import com.wed18305.assignment1.model.UserType.UserTypeID;
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
@RequestMapping("/api/booking")
public class Booking_Controller {

    @Autowired
    private Booking_Service bookingService;
    @Autowired
    private User_Service userService;
    @Autowired
    private Service_Service serviceService;

    /**
     * Create New Booking 
     * POST ENDPOINT: http://localhost:8080/api/booking/createBooking
     * INPUT JSON {"startDateTime":"yyyy-mm-dd hh:MM", (Format)
     *             "endDateTime"  :"2012-02-13 12:30",
     *             "customer_ids" : ["1", "2"], // Input an Array of Values (Considered valid)
     *             "employees_ids": ["5"], // Array with one (Both equally possible!)
     *             "service_id"   : 1,
     *             "requestID"    : 1
     */
    @PostMapping("createBooking")
    public ResponseEntity<Response> createNewBooking(@Valid @RequestBody Booking_Request br, BindingResult result) {

        User duplicateUser;

        // Binding validation checks
        if (result.hasErrors()) {
            
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Does Customers Column Only Contain Customer IDs?
        for (User user : userService.findManyById(br.getCustomerIds())) {

            if (!user.getType().getId().equals(UserTypeID.CUSTOMER.id)) {
                
                Response response = new Response(false, "ERROR! The user named '" + user.getName() + "' isn't registed as a Customer!", result.getFieldErrors(), null);
                return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
            }
        }

        // Has the Same User Been Inputted into the Customer Field Twice?
        duplicateUser = returnFirstDuplicate(userService.findManyById(br.getCustomerIds()));
        if (duplicateUser != null) {
                Response response = new Response(false, "ERROR! The user named '" + duplicateUser.getName() + "' was already entered!", result.getFieldErrors(), null);
                return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Does Employees Column Only Contain Employee IDs?
        for (User user : userService.findManyById(br.getEmployeeIds())) {

            if (!user.getType().getId().equals(UserTypeID.EMPLOYEE.id)) {
                
                Response response = new Response(false, "ERROR! The user named '" + user.getName() + "' isn't registed as a Employee!", result.getFieldErrors(), null);
                return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
            }
        }

        // Has the Same User Been Inputted into the Employee Field Twice?
        duplicateUser = returnFirstDuplicate(userService.findManyById(br.getEmployeeIds()));
        if (duplicateUser != null) {
                Response response = new Response(false, "ERROR! The user named '" + duplicateUser.getName() + "' was already entered!", result.getFieldErrors(), null);
                return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Save new Booking
        Booking booking1 = null;
        try {

            // Create a Booking entity using the Booking_Request
            Booking booking = new Booking(br.getStartDate(),
                                          br.getEndDate(),
                                          userService.findManyById(br.getCustomerIds()),
                                          userService.findManyById(br.getEmployeeIds()),
                                          serviceService.findById(br.getServiceId()).get());

            // Save Booking
            booking1 = bookingService.saveOrUpdateBooking(booking);

        } catch (Exception e) {
            
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Check booking result
        if (booking1 == null) {

            Response response = new Response(false, "ERROR!", "booking returned NULL", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);

        } else {

            Response response = new Response(true, "booking created!", null, booking1);
            return new ResponseEntity<Response>(response, HttpStatus.CREATED);
        }
    }

    // Helper Methods

    // Code Referenced From Here: https://stackoverflow.com/questions/7414667/identify-duplicates-in-a-list
    private boolean doesDuplicateExist(List<User> users) {
        
        Set<User> duplicateChecker = new HashSet<User>();

        for (User user : users) {

            if (!duplicateChecker.add(user)) {
                return true;
            }
        }

        return false;
    }

    private User returnFirstDuplicate(List<User> users) {
        
        Set<User> duplicateChecker = new HashSet<User>();

        for (User user : users) {

            if (!duplicateChecker.add(user)) {
                return user;
            }
        }

        return null;
    }

    /*
        Everything below here won't work.
        Apparently you can't create a ResponseEntity within a method. Even if the conditionals are met, the program will continue like nothing happened.
        Leaving it below for legacy purposes. Learn from this!
    */

    // private ResponseEntity<Response> customerInputValidation(Booking_Request br, BindingResult result) {

    //     // Does Employees Column Only Contain Employee IDs?
    //     doUsersHaveTheSameType(br.getCustomerIds(), UserTypeID.CUSTOMER.id, result);

    //     // Has the Same User Been Inputted into the Employee Field Twice?
    //     doesDuplicateExist(userService.findManyById(br.getCustomerIds()), br, result);

    //     return null;
    // }

    // private ResponseEntity<Response> employeeInputValidation(Booking_Request br, BindingResult result) {

    //     // Does Employees Column Only Contain Employee IDs?
    //     doUsersHaveTheSameType(br.getEmployeeIds(), UserTypeID.EMPLOYEE.id, result);

    //     // Has the Same User Been Inputted into the Employee Field Twice?
    //     doesDuplicateExist(userService.findManyById(br.getEmployeeIds()), br, result);

    //     return null;
    // }

    // private ResponseEntity<Response> doUsersHaveTheSameType(Long[] userIDs, Long userTypeID, BindingResult result) {

    //     for (User user : userService.findManyById(userIDs)) {

    //         if (!user.getType().getId().equals(userTypeID)) {
                
    //             Response response = new Response(false, "ERROR! The user named '" + user.getName() + "' isn't registed as a Employee!", result.getFieldErrors(), null);
    //             return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
    //         }
    //     }

    //     return null;
    // }

    // private ResponseEntity<Response> doesDuplicateExist(List<User> users, Booking_Request br, BindingResult result) {

    //     User duplicateUser = returnFirstDuplicate(users);
    //     if (duplicateUser != null) {
    //             Response response = new Response(false, "ERROR! The user named '" + duplicateUser.getName() + "' was already entered!", result.getFieldErrors(), null);
    //             return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
    //     }

    //     return null;
    // }
    
}