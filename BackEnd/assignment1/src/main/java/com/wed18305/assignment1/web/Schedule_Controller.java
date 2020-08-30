package com.wed18305.assignment1.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import com.wed18305.assignment1.model.User_model;
import com.wed18305.assignment1.Responses.Response;
import com.wed18305.assignment1.Requests.Schedule_Request;
import com.wed18305.assignment1.model.Schedule;
import com.wed18305.assignment1.services.Schedule_Service;
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
@RequestMapping("/api/schedule")
public class Schedule_Controller {

    @Autowired
    private Schedule_Service schService;
    @Autowired
    private User_Service usrService;

    /**
     * Create new schedule 
     * POST ENDPOINT: http://localhost:8080/api/schedule/createSchedule
     * INPUT JSON {"startDateTime":"yyyy-mm-dd hh:MM", 
     *             "endDateTime":"yyyy-mm-dd hh:MM", 
     *             "user_ids":["1"]}
     */
    @PostMapping("createSchedule")
    public ResponseEntity<Response> createNewSchedule(@Valid @RequestBody Schedule_Request sr, BindingResult result) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Save new Schedule from request body
        Schedule s1 = null;
        try {
            List<User_model> employees = new ArrayList<User_model>();
            employees = usrService.findManyEmployeesById(sr.getUserIds());
            if(employees.size() == 0){
                Response response = new Response(false, "ERROR!", "No employees passed in", null);
                return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
            }
            s1 = new Schedule(sr.getStartDate(),sr.getEndDate(),employees);
            schService.saveOrUpdateUser(s1);
        } catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        // Check user result
        if (s1 == null) {
            Response response = new Response(false, "ERROR!", "schedule returned NULL", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } else {
            Response response = new Response(true, "schedule created!", null, s1);
            return new ResponseEntity<Response>(response, HttpStatus.CREATED);
        }
    }
}