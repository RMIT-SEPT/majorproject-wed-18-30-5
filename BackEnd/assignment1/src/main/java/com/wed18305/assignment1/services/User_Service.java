package com.wed18305.assignment1.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wed18305.assignment1.model.Entity_Service;
import com.wed18305.assignment1.model.Entity_Booking;
import com.wed18305.assignment1.model.Entity_Schedule;
import com.wed18305.assignment1.model.Entity_User;
import com.wed18305.assignment1.model.Entity_UserType.UserTypeID;
import com.wed18305.assignment1.repositories.Booking_Repository;
import com.wed18305.assignment1.repositories.Schedule_Repository;
import com.wed18305.assignment1.repositories.Service_Repository;
import com.wed18305.assignment1.repositories.User_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class User_Service {
    @Autowired
    private User_Repository userRepository;
    @Autowired
    private Booking_Repository bookingRepository;
    @Autowired
    private Service_Repository serviceRepository;
    @Autowired
    private Schedule_Repository scheduleRepository;

    public Entity_User saveOrUpdateUser(Entity_User user) {
        return userRepository.save(user);
    }

    public Optional<Entity_User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<Entity_User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public Optional<Entity_User> findById(Long id){
        return userRepository.findById(id);
    }

    public List<Entity_User> findManyById(Long[] ids){
        
        List<Entity_User> users = new ArrayList<Entity_User>();

        for (int i = 0; i < ids.length; i++) {
            users.add(userRepository.findById(ids[i]).get());
        }

        return users;
    }
    //Employees are users that are either employees or admins
    public ArrayList<Entity_User> findManyEmployeesById(Long[] ids){   
        ArrayList<Entity_User> users = new ArrayList<Entity_User>();
        for (int i = 0; i < ids.length; i++) {
            Entity_User u = userRepository.findById(ids[i]).get();
            if(u.getType().getId() == 1 || u.getType().getId() == 2){
                users.add(u);
            }
        }
        return users;
    }

    public Iterable<Entity_User> findAllByTypeId(Long id){
        return userRepository.findAllByUserTypeId(id);
    }

    // What Bookings has a Customer Made?
    public List<Entity_Booking> findUserBookings(Long id) {
        Entity_User user = userRepository.findById(id).get();

        List<Entity_Booking> userBookings = new ArrayList<Entity_Booking>();

        for (Entity_Booking booking : bookingRepository.findAll()) {

            if (booking.getCustomers().contains(user)) {
                userBookings.add(booking);
            }
        }

        return userBookings;
    }

    public List<Entity_Booking> findUpcomingUserBookings(Long id) {
        return returnUpcoming(id, false);
    }

    public List<Entity_Booking> findCompletedUserBookings(Long id) {
        return returnCompleted(id, false);
    }

    // What Approved Bookings does an Employee Have?
    public List<Entity_Booking> findApprovedUserBookings(Long id) {
        Entity_User user = userRepository.findById(id).get();

        List<Entity_Booking> userBookings = new ArrayList<Entity_Booking>();

        for (Entity_Booking booking : bookingRepository.findAll()) {

            if (booking.getEmployees().contains(user) && booking.getApproved()) {
                userBookings.add(booking);
            }
        }

        return userBookings;
    }

    public List<Entity_Booking> findApprovedUpcomingBookings(Long id) {
        return returnUpcoming(id, true);
    }

    public List<Entity_Booking> findApprovedCompletedBookings(Long id) {
        return returnCompleted(id, true);
    }

    private ArrayList<Entity_Booking> returnUpcoming(Long id, Boolean isApproved) {
        Entity_User user = userRepository.findById(id).get();

        ArrayList<Entity_Booking> bookings = new ArrayList<>();

        for (Entity_Booking booking : bookingRepository.findAll()) {

            // Booking hasn't Occurred, or Finished Yet
            if (isApproved && booking.getApproved() || !isApproved) {
                if ((booking.getEmployees().contains(user) || booking.getCustomers().contains(user)) 
                     && LocalDateTime.now().compareTo(booking.getEndDateTime()) < 0) {
                    bookings.add(booking);
                }
            }
        }

        return bookings;
    } 

    private ArrayList<Entity_Booking> returnCompleted(Long id, Boolean checkingApproval) {
        Entity_User user = userRepository.findById(id).get();

        ArrayList<Entity_Booking> bookings = new ArrayList<>();

        for (Entity_Booking booking : bookingRepository.findAll()) {

            // Booking has Occurred
            if (checkingApproval && booking.getApproved() || !checkingApproval) {
                if ((booking.getEmployees().contains(user) || booking.getCustomers().contains(user)) 
                     && LocalDateTime.now().compareTo(booking.getEndDateTime()) > 0) {
                    bookings.add(booking);
                }
            }
        }

        return bookings;
    } 

    /**
     * Note that if you alter a service after creating it for multiple users
     * it will update for ALL users
     * @param users
     * @param services
     * @return
     */
    public Iterable<Entity_User> addServicesToEmployees(ArrayList<Entity_User> users, ArrayList<Entity_Service> services){
        //Add the services to the users
        for (Entity_User u : users) {
            for (Entity_Service service: services) {
                //Check that the user doesn't already have the service.
                if (u.getServices().contains(service) == false){
                    u.getServices().add(service);
                }
            }
        }
        //save the users
        return userRepository.saveAll(users);
    }

    /**
     * Note that if you alter a schedule after creating it for multiple users
     * it will update for ALL users
     * @param users
     * @param schedules
     * @return
     */
    public Iterable<Entity_User> addSchedulesToEmployees(ArrayList<Entity_User> users, ArrayList<Entity_Schedule> schedules){
        //Add the services to the users
        for (Entity_User u : users) {
            for (Entity_Schedule schedule : schedules) {
                //Check that the user doesn't already have the schedule.
                if (u.getSchedules().contains(schedule) == false){
                    u.getSchedules().add(schedule);
                }
            }
        }
        //save the users
        return userRepository.saveAll(users);
    }
    


    //DELETION
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
    public void deleteAll(Iterable<Entity_User> users){
        userRepository.deleteAll(users);
    }
}