package com.wed18305.assignment1.web;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import com.wed18305.assignment1.Responses.Response;
import com.wed18305.assignment1.Requests.Booking_Request;
import com.wed18305.assignment1.Requests.Delete_Request;
import com.wed18305.assignment1.Requests.Get_Request;
import com.wed18305.assignment1.model.Entity_Booking;
import com.wed18305.assignment1.services.Booking_Service;
// import com.wed18305.assignment1.services.Service_Service;
import com.wed18305.assignment1.model.Entity_User;
import com.wed18305.assignment1.model.Entity_UserType.UserTypeID;
import com.wed18305.assignment1.services.User_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class Booking_Controller {

    @Autowired
    private Booking_Service bookingService;
    @Autowired
    private User_Service userService;
    // @Autowired
    // private Service_Service serviceService;

    /**
     * Create New Booking 
     * POST ENDPOINT: http://localhost:8080/api/booking/createBooking
     * INPUT JSON {"startDateTime":"dd/mm/yyyyThh:MM:00 UTC+nn:nn", (Format)
     *             "endDateTime"  :"03/08/2019T16:20:00 UTC+05:30",
     *             "customer_ids" : ["1", "2"], // Input an Array of Values (Considered valid)
     *             "employees_ids": ["5"], // Array with one (Both equally possible!)
     *             "service_id"   : 1
     */
    @PostMapping("createBooking")
    public ResponseEntity<Response> createNewBooking(@Valid @RequestBody Booking_Request br, BindingResult result) {

        Entity_User duplicateUser;

        // Binding validation checks
        if (result.hasErrors()) {
            
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Does Customers Column Only Contain Customer IDs?
        for (Entity_User user : userService.findManyById(br.getCustomerIds())) {

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
        for (Entity_User user : userService.findManyById(br.getEmployeeIds())) {

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
        Entity_Booking booking1 = null;
        try {

            // Create a Booking entity using the Booking_Request
            Entity_Booking booking = new Entity_Booking(br.getStartDate(),
                                          br.getEndDate(),
                                          userService.findManyById(br.getCustomerIds()),
                                          userService.findManyById(br.getEmployeeIds()));

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

    /**
     * Approve Existing Booking 
     * PATCH ENDPOINT: http://localhost:8080/api/booking/approveBooking
     * INPUT JSON {"id":1 }
     */
    @PatchMapping("approveBooking")
    public ResponseEntity<Response> approveBooking(@Valid @RequestBody Get_Request gr, BindingResult result) {

        // Binding validation checks
        if (result.hasErrors()) {
            
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Attempt to Find Booking by ID
        Optional<Entity_Booking> book = bookingService.findById(gr.getId());
        if (book == null) {
            Response response = new Response(false, "ERROR!", "Booking doesn't exist!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Attempt to Approve Booking
        try {
            Entity_Booking currentBooking = book.get();
            currentBooking.approveBooking();
            bookingService.saveOrUpdateBooking(currentBooking);
        }
        catch (Exception e) {
            Response response = new Response(false, "ERROR!", "Booking couldn't be updated!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
         }

        Response response = new Response(true, "Booking approved!", null, book);
        return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
    }

    /**
     * Get All Bookings (Admins can view all bookings, if they're not more than 7 days old)
     * GET ENDPOINT: http://localhost:8080/api/booking/getAdminBookings
     */
    @GetMapping("getAdminBookings")
    public ResponseEntity<Response> getAdminBookings(Principal p) {

        // Make sure the logged in user exists
        Optional<Entity_User> user = userService.findByUsername(p.getName());
        if(user.isPresent() == false){
            //shouldn't be able to get here but just incase
            Response response = new Response(false, "ERROR!", "Nobody's logged in!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        
        // Get All Bookings
        Iterable<Entity_Booking> bookings =  null;
        try {
            bookings = bookingService.findAll();
        }
        catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Any Bookings Found?
        if (!bookings.iterator().hasNext()) { // If size < 1
            Response response = new Response(false, "ERROR!", "No bookings within the repository!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "Bookings found!", null, bookings);
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
    }

    /**
     * Get An Employee's Bookings (That have been approved.)
     * GET ENDPOINT: http://localhost:8080/api/booking/getEmployeeBookings
     */
    @GetMapping("getEmployeeBookings")
    public ResponseEntity<Response> getEmployeeBookings(Principal p) {

        // Make sure the logged in user exists
        Optional<Entity_User> user = userService.findByUsername(p.getName());
        if(user.isPresent() == false){
            //shouldn't be able to get here but just incase
            Response response = new Response(false, "ERROR!", "Nobody's logged in!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Get the Employee's Bookings
        Entity_User curUser = user.get();
        Iterable<Entity_Booking> bookings =  null;
        try {
            bookings = userService.findApprovedUserBookings(curUser.getId());
        }
        catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Any Bookings Found?
        if (!bookings.iterator().hasNext()) { // If size < 1
            Response response = new Response(false, "ERROR!", "Employee has no approved bookings associated with them!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "Bookings found!", null, bookings);
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
    }

    /**
     * Get A Customer's Bookings
     * GET ENDPOINT: http://localhost:8080/api/booking/getCustomerBookings
     */
    @GetMapping("getCustomerBookings")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Response> getCustomerBookings(Principal p) {

        // Make sure the logged in user exists
        Optional<Entity_User> user = userService.findByUsername(p.getName());
        if(user.isPresent() == false){
            //shouldn't be able to get here but just incase
            Response response = new Response(false, "ERROR!", "Nobody's logged in!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Get the Customer's Bookings
        Entity_User curUser = user.get();
        Iterable<Entity_Booking> bookings =  null;
        try {
            bookings = userService.findUserBookings(curUser.getId());
        }
        catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Any Bookings Found?
        if (!bookings.iterator().hasNext()) { // If size < 1
            Response response = new Response(false, "ERROR!", "Customer has made no bookings!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "Bookings found!", null, bookings);
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
    }

    /**
     * Get All Upcoming Bookings
     * GET ENDPOINT: http://localhost:8080/api/booking/getUpcomingAdminBookings
     */
    @GetMapping("getUpcomingAdminBookings")
    public ResponseEntity<Response> getUpcomingAdminBookings(Principal p) {

        // Make sure the logged in user exists
        Optional<Entity_User> user = userService.findByUsername(p.getName());
        if(user.isPresent() == false){
            //shouldn't be able to get here but just incase
            Response response = new Response(false, "ERROR!", "Nobody's logged in!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Get All Bookings
        Iterable<Entity_Booking> bookings =  null;
        try {
            bookings = bookingService.findAllUpcoming();
        }
        catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Any Bookings Found?
        if (!bookings.iterator().hasNext()) { // If size < 1
            Response response = new Response(false, "ERROR!", "No bookings within the repository!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "Bookings found!", null, bookings);
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
    }

    /**
     * Get All Completed Bookings
     * GET ENDPOINT: http://localhost:8080/api/booking/getCompletedAdminBookings
     */
    @GetMapping("getCompletedAdminBookings")
    public ResponseEntity<Response> getCompletedAdminBookings(Principal p) {

        // Make sure the logged in user exists
        Optional<Entity_User> user = userService.findByUsername(p.getName());
        if(user.isPresent() == false){
            //shouldn't be able to get here but just incase
            Response response = new Response(false, "ERROR!", "Nobody's logged in!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Get All Bookings
        Iterable<Entity_Booking> bookings =  null;
        try {
            bookings = bookingService.findAllCompleted();
        }
        catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Any Bookings Found?
        if (!bookings.iterator().hasNext()) { // If size < 1
            Response response = new Response(false, "ERROR!", "No bookings within the repository!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "Bookings found!", null, bookings);
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
    }

    /**
     * Get an Employee's Upcoming Bookings
     * GET ENDPOINT: http://localhost:8080/api/booking/getUpcomingEmployeeBookings
     */
    @GetMapping("getUpcomingEmployeeBookings")
    public ResponseEntity<Response> getUpcomingEmployeeBookings(Principal p) {

        // Make sure the logged in user exists
        Optional<Entity_User> user = userService.findByUsername(p.getName());
        if(user.isPresent() == false){
            //shouldn't be able to get here but just incase
            Response response = new Response(false, "ERROR!", "Nobody's logged in!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Get Employee's Bookings
        Entity_User curUser = user.get();
        Iterable<Entity_Booking> bookings =  null;
        try {
            bookings = userService.findApprovedUpcomingBookings(curUser.getId());
        }
        catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Any Bookings Found?
        if (!bookings.iterator().hasNext()) { // If size < 1
            Response response = new Response(false, "ERROR!", "Employee has no approved bookings associated with them coming up!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "Bookings found!", null, bookings);
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
    }

    /**
     * Get an Employee's Completed Bookings
     * GET ENDPOINT: http://localhost:8080/api/booking/getCompletedEmployeeBookings
     */
    @GetMapping("getCompletedEmployeeBookings")
    public ResponseEntity<Response> getCompletedEmployeeBookings(Principal p) {

        // Make sure the logged in user exists
        Optional<Entity_User> user = userService.findByUsername(p.getName());
        if(user.isPresent() == false){
            //shouldn't be able to get here but just incase
            Response response = new Response(false, "ERROR!", "Nobody's logged in!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Get Employee's Bookings
        Entity_User curUser = user.get();
        Iterable<Entity_Booking> bookings =  null;
        try {
            bookings = userService.findApprovedCompletedBookings(curUser.getId());
        }
        catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Any Bookings Found?
        if (!bookings.iterator().hasNext()) { // If size < 1
            Response response = new Response(false, "ERROR!", "Employee has not completed any approved bookings associated with them!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "Bookings found!", null, bookings);
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
    }

    /**
     * Get a Customer's Upcoming Bookings
     * GET ENDPOINT: http://localhost:8080/api/booking/getUpcomingCustomerBookings
     */
    @GetMapping("getUpcomingCustomerBookings")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Response> getUpcomingCustomerBookings(Principal p) {

        // Make sure the logged in user exists
        Optional<Entity_User> user = userService.findByUsername(p.getName());
        if(user.isPresent() == false){
            //shouldn't be able to get here but just incase
            Response response = new Response(false, "ERROR!", "Nobody's logged in!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Get Customer's Bookings
        Entity_User curUser = user.get();
        Iterable<Entity_Booking> bookings =  null;
        try {
            bookings = userService.findUpcomingUserBookings(curUser.getId());
        }
        catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Any Bookings Found?
        if (!bookings.iterator().hasNext()) { // If size < 1
            Response response = new Response(false, "ERROR!", "Customer has no bookings associated with them coming up!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "Bookings found!", null, bookings);
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
    }

    /**
     * Get a Customer's Completed Bookings
     * GET ENDPOINT: http://localhost:8080/api/booking/getCompletedCustomerBookings
     */
    @GetMapping("getCompletedCustomerBookings")
    public ResponseEntity<Response> getCompletedCustomerBookings(Principal p) {

        // Make sure the logged in user exists
        Optional<Entity_User> user = userService.findByUsername(p.getName());
        if(user.isPresent() == false){
            //shouldn't be able to get here but just incase
            Response response = new Response(false, "ERROR!", "Nobody's logged in!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Get Customer's Bookings
        Entity_User curUser = user.get();
        Iterable<Entity_Booking> bookings =  null;
        try {
            bookings = userService.findCompletedUserBookings(curUser.getId());
        }
        catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Any Bookings Found?
        if (!bookings.iterator().hasNext()) { // If size < 1
            Response response = new Response(false, "ERROR!", "Customer has not completed any bookings associated with them!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "Bookings found!", null, bookings);
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
    }

    /**
     * Delete Existing Booking 
     * POST ENDPOINT: http://localhost:8080/api/booking/deleteBooking
     * INPUT JSON {"input":[1] }
     */
    @DeleteMapping("deleteBooking")
    public ResponseEntity<Response> deleteBooking(@Valid @RequestBody Delete_Request dr, BindingResult result) {

        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            bookingService.deleteManyById(dr.getLong());
        }
        catch (IllegalArgumentException e) {
            Response response = new Response(false, "ERROR!", "Id of booking is null!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            Response response = new Response(false, "ERROR!", "Unable to delete booking!", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        Response response = new Response(true, "Booking deleted!", null, null);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    // Helper Methods
    private Entity_User returnFirstDuplicate(List<Entity_User> users) {
        
        Set<Entity_User> duplicateChecker = new HashSet<Entity_User>();

        for (Entity_User user : users) {

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