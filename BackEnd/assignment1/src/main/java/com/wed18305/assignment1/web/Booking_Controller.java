package com.wed18305.assignment1.web;

import java.util.Optional;

import javax.validation.Valid;

import com.wed18305.assignment1.Responses.Response;
import com.wed18305.assignment1.Requests.Booking_Request;
import com.wed18305.assignment1.model.Booking;
import com.wed18305.assignment1.services.Booking_Service;
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

    /**
     * Create new (customer)user 
     * POST ENDPOINT: http://localhost:8080/api/booking/createBooking
     * INPUT JSON {"startDateTime":"yyyy-mm-dd hh:MM", (Format)
     *             "endDateTime"  :"2012-02-13 12:30",
     *             "customer_ids": ["1", "2"], // Input an Array of Values (Considered valid)
     *             "worker_ids": ["5"], // Array with one (Both equally possible!)
     *             "requestID":"1"
     */
    @PostMapping("createBooking")
    public ResponseEntity<Response> createNewBooking(@Valid @RequestBody Booking_Request br, BindingResult result) {

        // Binding validation checks
        if (result.hasErrors()) {
            
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Save new Booking
        Booking booking1 = null;
        try {

            // Create a Booking entity using the Booking_Request
            Booking booking = new Booking(br.getStartDate(),
                                          br.getEndDate(),
                                          userService.findManyById(br.getCustomerIds()),
                                          userService.findManyById(br.getWorkerIds()));

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

}