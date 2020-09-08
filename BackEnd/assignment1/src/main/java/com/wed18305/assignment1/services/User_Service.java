package com.wed18305.assignment1.services;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.wed18305.assignment1.model.Entity_Service;
import com.wed18305.assignment1.model.Entity_Booking;
import com.wed18305.assignment1.model.Entity_Schedule;
import com.wed18305.assignment1.model.Entity_User;
import com.wed18305.assignment1.repositories.Booking_Repository;
// import com.wed18305.assignment1.repositories.Schedule_Repository;
// import com.wed18305.assignment1.repositories.Service_Repository;
import com.wed18305.assignment1.repositories.User_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class User_Service {
    @Autowired
    private User_Repository userRepository;
    @Autowired
    private Booking_Repository bookingRepository;
    // @Autowired
    // private Service_Repository serviceRepository;
    // @Autowired
    // private Schedule_Repository scheduleRepository;

    public Entity_User saveOrUpdateUser(Entity_User user) {
        return userRepository.save(user);
    }

    public Optional<Entity_User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<Entity_User> findById(Long id){
        return userRepository.findById(id);
    }

    public Iterable<Entity_User> findByIds(Long[] ids){
        Set<Long> idsSet = new HashSet<Long>();
        for (Long id : ids) {
            try {
                idsSet.add(id);
            } catch (Exception e) {
                System.err.println(e.getClass().getCanonicalName());
                System.out.println(e.getMessage());
            }
        }
        //Get the ids
        return userRepository.findAllById(idsSet);
    }

    //Employees are users that are either employees or admins
    public Iterable<Entity_User> findEmployeesById(Long[] ids){   
        Set<Entity_User> users = new HashSet<Entity_User>();
        for (int i = 0; i < ids.length; i++) {
            Entity_User u = userRepository.findById(ids[i]).get();
            if(u.getType().getId() == 1 || u.getType().getId() == 2){
                try {
                    users.add(u);
                } catch (Exception e) {
                    System.out.println(e.getClass().getCanonicalName());
                    System.out.println(e.getMessage());
                }
            }
        }
        return users;
    }

    public Iterable<Entity_User> findAllByTypeId(Long id){
        return userRepository.findAllByUserTypeId(id);
    }

    // What Bookings has a Customer Made? may be null
    public Iterable<Entity_Booking> findUserBookings(Long id) {
        Entity_User user = userRepository.findById(id).get();
        return user.getBookings();
    }

    public Iterable<Entity_Booking> findUpcomingUserBookings(Long id) {
        return returnUpcoming(id, false);
    }

    public Iterable<Entity_Booking> findCompletedUserBookings(Long id) {
        return returnCompleted(id, false);
    }

    // What Approved Bookings does an Employee Have?
    public Iterable<Entity_Booking> findApprovedUserBookings(Long id) {
        Entity_User user = userRepository.findById(id).get();
        if(user.getBookings() == null){
            return null;
        }
        Set<Entity_Booking> userBookings = new HashSet<Entity_Booking>();
        for (Entity_Booking booking :user.getBookings()) {
            if(booking.getApproved()){
                userBookings.add(booking);
            }
        }
        return userBookings;
    }

    public Iterable<Entity_Booking> findApprovedUpcomingBookings(Long id) {
        return returnUpcoming(id, true);
    }

    public Iterable<Entity_Booking> findApprovedCompletedBookings(Long id) {
        return returnCompleted(id, true);
    }

    private Iterable<Entity_Booking> returnUpcoming(Long id, Boolean isApproved) {
        Entity_User user = userRepository.findById(id).get();
        if(user == null){
            return null;
        }
        if(user.getBookings() == null){
            return null;
        }
        Set<Entity_Booking> userBookings = new HashSet<Entity_Booking>();
        for (Entity_Booking booking : user.getBookings()) {
            if(isApproved && booking.getApproved() || !isApproved){
                if(OffsetDateTime.now().compareTo(booking.getStartDateTime()) < 0){
                    userBookings.add(booking);
                }
            }
        }
        return userBookings;
    } 

    private Iterable<Entity_Booking> returnCompleted(Long id, Boolean checkingApproval) {
        Entity_User user = userRepository.findById(id).get();
        if(user == null){
            return null;
        }
        if(user.getBookings() == null){
            return null;
        }
        Set<Entity_Booking> userBookings = new HashSet<Entity_Booking>();
        for (Entity_Booking booking : user.getBookings()) {
            if(checkingApproval && booking.getApproved() || !checkingApproval){
                if(OffsetDateTime.now().compareTo(booking.getEndDateTime()) > 0){
                    userBookings.add(booking);
                }
            }
        }
        return userBookings;
    } 

    /**
     * Note that if you alter a service after creating it for multiple users
     * it will update for ALL users
     * @param users
     * @param services
     * @return
     */
    public Iterable<Entity_User> addServicesToEmployees(Iterable<Entity_User> users,
                                                        Iterable<Entity_Service> services)
                                                        throws NullPointerException{
        if(users == null || services == null){
            throw new NullPointerException("users or services cannot be null");
        }
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
    public Iterable<Entity_User> addSchedulesToEmployees(Iterable<Entity_User> users,
                                                         Iterable<Entity_Schedule> schedules)
                                                         throws NullPointerException{
        if(users == null || schedules == null){
            throw new NullPointerException("users or schedules cannot be null");
        }
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

    /**
     * Note that if you alter a booking after creating it for multiple users
     * it will update for ALL users
     * @param users
     * @param schedules
     * @return
     */
    public Iterable<Entity_User> addBookingsToEmployees(Iterable<Entity_User> users,
                                                        Iterable<Entity_Booking> bookings)
                                                        throws NullPointerException{
        if(users == null || bookings == null){
            throw new NullPointerException("users or bookings cannot be null");
        }
        //Add the services to the users
        for (Entity_User u : users) {
            for (Entity_Booking booking : bookings) {
                //Check that the user doesn't already have the schedule.
                if (u.getBookings().contains(booking) == false){
                    u.getBookings().add(booking);
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