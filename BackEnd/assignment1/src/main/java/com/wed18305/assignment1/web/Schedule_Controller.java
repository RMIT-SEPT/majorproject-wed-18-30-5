package com.wed18305.assignment1.web;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import javax.validation.Valid;
import com.wed18305.assignment1.model.Entity_User;
import com.wed18305.assignment1.Responses.Response;
import com.wed18305.assignment1.Requests.Delete_Request;
import com.wed18305.assignment1.Requests.Get_Request;
import com.wed18305.assignment1.Requests.Schedule_Request;
import com.wed18305.assignment1.model.Entity_Schedule;
import com.wed18305.assignment1.services.Booking_Service;
import com.wed18305.assignment1.services.Schedule_Service;
import com.wed18305.assignment1.services.User_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
@CrossOrigin(origins = "http://localhost:3000")
public class Schedule_Controller {

    @Autowired
    private Schedule_Service schService;
    @Autowired
    private User_Service userService;
    // @Autowired
    // private Booking_Service bkngService;
    //TODO schedules will need to handle and array of start and end times
    /**
     * Create new schedule
     * <p>
     * POST ENDPOINT: http://localhost:8080/api/schedule/createSchedule
     * <p>
     * INPUT JSON {"startDateTime":"2020-05-05T08:45+10:00", "endDateTime":"2020-05-05T08:50+10:00", "user_ids":["1"]}
     * 
     * @param sr
     * @param result
     * @return Response
     */
    @PostMapping("createSchedule")
    public ResponseEntity<Response> createSchedule(@Valid @RequestBody Schedule_Request sr, BindingResult result) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //Check the datetime format/make sure that the start time is before the endtime
        if(sr.getStartDate().isAfter(sr.getEndDate())){
            //Could alternatively just flip the times...
            Response response = new Response(false, "ERROR!", "Start date cant be after end date", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //Check that the employee ids provided are employees
        ArrayList<Entity_User> employees = (ArrayList<Entity_User>) userService.findEmployeesById(sr.getUserIds());
        if (employees.size() == 0) {
            Response response = new Response(false, "ERROR!", "No employees passed in", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //Create new Schedule from request body
        Entity_Schedule s1 = null;
        ArrayList<Entity_Schedule> schedules = new ArrayList<Entity_Schedule>();
        try {
            //Adjust the time back to UTC zero hour
            OffsetDateTime sdUTC = sr.getStartDate().withOffsetSameInstant(ZoneOffset.UTC);
            OffsetDateTime edUTC = sr.getEndDate().withOffsetSameInstant(ZoneOffset.UTC);
            s1 = new Entity_Schedule(sdUTC, edUTC);
            schedules.add(s1);
        } catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //Make sure the schedule doesn't already exist for an employee.
        for (Entity_User employee : employees) {
            for(Entity_Schedule schedule: employee.getSchedules()){
                if(schedule.equals(s1)){
                    Response response = new Response(false, "ERROR!", "Schedule overlaps an existing one for "+employee.getName(), null);
                    return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
                }
            }
        }
        //Save the changes in the database
        try {
            s1 = schService.saveOrUpdateSchedule(s1);
            userService.addSchedulesToEmployees(employees, schedules);
        } catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //Success
        Response response = new Response(true, "schedule created!", null, s1);
        return new ResponseEntity<Response>(response, HttpStatus.CREATED);
    }

    /**
     * delete schedule, only an admin can call this.
     * <p>
     * POST ENDPOINT: http://localhost:8080/api/schedule/deleteSchedule
     * <p>
     * INPUT JSON {"input":["1"]}
     * 
     * @param dr
     * @param result
     * @return Response
     */
    @PostMapping("deleteSchedule")
    public ResponseEntity<Response> deleteSchedule(@Valid @RequestBody Delete_Request dr, BindingResult result,
            Principal p) {
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        // Request should have the ids as a Long[]
        Long[] schedules = dr.getLong();
        if (schedules == null) {
            Response response = new Response(false, "ERROR!", "No valid Long[] ids passed in", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //Get the schedules from the DB
        ArrayList<Entity_Schedule> schedules_db = (ArrayList<Entity_Schedule>) schService.findByIds(schedules);
        if(schedules_db.size()==0){ //No schedule found in the DB
            Response response = new Response(false, "ERROR!", "No schedules found in the database", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //TODO check if the schedule has booking.

        //Now delete the actual schedules
        try {      
            schService.deleteAll(schedules_db);
        } catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //Success
        Response response = new Response(true, "schedule(s) deleted!", null, null);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    /**
     * Get user schedule(s)
     * <p>
     * GET ENDPOINT: http://localhost:8080/api/schedule/getSchedule
     * <p>
     * INPUT JSON {"id":"1"}  -id is the userID
     * @param dr
     * @param result
     * @return
     */
    @CrossOrigin("http://localhost:3000")
    @PostMapping("getSchedule")
    public ResponseEntity<Response> getSchedule(@Valid @RequestBody Get_Request gr, BindingResult result){
        // Binding validation checks
        if (result.hasErrors()) {
            Response response = new Response(false, "ERROR!", result.getFieldErrors(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //Check that the employee id provided are employees
        Long id[] = {gr.getId()};  
        ArrayList<Entity_User> employees = (ArrayList<Entity_User>) userService.findEmployeesById(id);
        if (employees.size() == 0) {
            Response response = new Response(false, "ERROR!", "No employees passed in", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //Get a users schedule from now -> month from now
        OffsetDateTime ODT_Now = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime ODT_Month = ODT_Now.plusMonths(1);
        ArrayList<Entity_Schedule> schedules = new ArrayList<Entity_Schedule>();
        for (Entity_User employee : employees) {
            for(Entity_Schedule schedule: employee.getSchedules()){
                if(schedule.getStartDateTime().isAfter(ODT_Now)){
                    if(schedule.getStartDateTime().isBefore(ODT_Month)){
                        //Java OffsetDateTime automatically converts the time to machine local... revert that
                        schedule.setStartDateTime(schedule.getStartDateTime().withOffsetSameInstant(ZoneOffset.UTC));
                        schedule.setEndDateTime(schedule.getEndDateTime().withOffsetSameInstant(ZoneOffset.UTC));
                        schedules.add(schedule);
                    }
                }
            }
        }
        //Are there any schedules
        if(schedules.isEmpty()){
            //ERROR
            Response response = new Response(false, "No schedule(s) found!", null, null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //Success
        Response response = new Response(true, "schedule(s) found!", null, schedules);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
    /**
     * 
     * @param dr
     * @param result
     * @return
     */
    @GetMapping("updateSchedule")
    public ResponseEntity<Response> updateSchedule(@Valid @RequestBody Delete_Request dr, BindingResult result){
        //TODO implement
        //Success
        Response response = new Response(true, "schedule(s) found!", null, null);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}