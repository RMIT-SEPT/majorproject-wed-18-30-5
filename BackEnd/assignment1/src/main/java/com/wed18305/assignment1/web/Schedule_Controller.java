package com.wed18305.assignment1.web;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import com.wed18305.assignment1.model.Entity_User;
import com.wed18305.assignment1.Responses.Response;
import com.wed18305.assignment1.Requests.Delete_Request;
import com.wed18305.assignment1.Requests.Schedule_Request;
import com.wed18305.assignment1.model.Entity_Schedule;
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
     * <p>
     * POST ENDPOINT: http://localhost:8080/api/schedule/createSchedule
     * <p>
     * INPUT JSON {"startDateTime":"uuuu-MM-dd'T'HH:mmXXXXX", "endDateTime":"uuuu-MM-dd'T'HH:mmXXXXX", "user_ids":["1"]}
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
        ArrayList<Entity_User> employees = new ArrayList<Entity_User>();
        employees = usrService.findManyEmployeesById(sr.getUserIds());
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
            schService.saveOrUpdateUser(s1);
            usrService.addSchedulesToEmployees(employees, schedules);
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
        ArrayList<Entity_Schedule> schedules_db = schService.findByIds(schedules);
        if(schedules_db.size()==0){ //No schedule found in the DB
            Response response = new Response(false, "ERROR!", "No schedules found in the database", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //Since the user is the owner of the user_schedules ref table we have to remove from there.
        try {
            for (int i = 0; i < schedules_db.size(); i++) {
                List<Entity_User> users = schedules_db.get(i).getEmployees();
                for (int j = 0; j < users.size(); j++) {
                    users.get(j).getSchedules().remove(schedules_db.get(i));
                    usrService.saveOrUpdateUser(users.get(j));
                }
            }
            //Now delete the actual schedules
            schService.deleteAll(schedules_db);
        } catch (Exception e) {
            Response response = new Response(false, "ERROR!", e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        //Success
        Response response = new Response(true, "schedule(s) deleted!", null, null);
        return new ResponseEntity<Response>(response, HttpStatus.CREATED);
    }
}