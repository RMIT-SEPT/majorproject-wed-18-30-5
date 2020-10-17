package com.wed18305.assignment1.services;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.wed18305.assignment1.model.Entity_Service;
import com.wed18305.assignment1.model.Entity_Booking;
import com.wed18305.assignment1.model.Entity_Schedule;
import com.wed18305.assignment1.model.Entity_User;
import com.wed18305.assignment1.model.Entity_Booking.Status;
import com.wed18305.assignment1.repositories.User_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class User_Service {
    @Autowired
    private User_Repository userRepository;
    // @Autowired
    // private Booking_Repository bookingRepository;
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

    public Iterable<Entity_User> findAllById(Long[] ids){
        List<Long> idsSet = new ArrayList<Long>();
        for (Long id : ids) {
            try {
                idsSet.add(id);
            } catch (Exception e) {
                System.out.println(e.getClass().getCanonicalName());
                System.out.println(e.getMessage());
            }
        }
        //Get the ids
        return userRepository.findAllById(idsSet);
    }

    //Employees are users that are either employees or admins
    public Iterable<Entity_User> findEmployeesById(Long[] ids){   
        List<Entity_User> users = new ArrayList<Entity_User>();
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
        ArrayList<Entity_Booking> bookings = new ArrayList<Entity_Booking>();

        for (Entity_Booking booking : user.getBookings()) {

            // Does Booking Exist Within the Appropriate Time Frame?
            if (bookingWillRunInSevenDays(booking)       || 
                bookingRanLessThanSevenDaysAgo(booking)) { bookings.add(booking); }
        }
        return bookings;
    }

    public Iterable<Entity_Booking> findUpcomingUserBookings(Long id) {
        return returnUpcoming(id, Status.getAny());
    }

    public Iterable<Entity_Booking> findCompletedUserBookings(Long id) {
        return returnCompleted(id, Status.getAny());
    }

    // What Approved Bookings does an Employee Have?
    public Iterable<Entity_Booking> findApprovedUserBookings(Long id) {
        Entity_User user = userRepository.findById(id).get();
        if(user.getBookings() == null){
            return null;
        }
        Set<Entity_Booking> userBookings = new HashSet<Entity_Booking>();
        for (Entity_Booking booking :user.getBookings()) {
            if(booking.getStatus() == Status.getApproved()){

                // Is Booking to be Run in Less than 7 Days?
                if (bookingWillRunInSevenDays(booking)) { userBookings.add(booking); }
            }
        }
        return userBookings;
    }

    public Iterable<Entity_Booking> findApprovedUpcomingBookings(Long id) {
        return returnUpcoming(id, Status.getApproved());
    }

    public Iterable<Entity_Booking> findApprovedCompletedBookings(Long id) {
        return returnCompleted(id, Status.getApproved());
    }

    // Which Bookings Have Been Denied?
    public Iterable<Entity_Booking> findDeniedUserBookings(Long id) {
        return returnBookings(id, Status.getDenied());
    }

    //// Helper Methods
    private Iterable<Entity_Booking> returnBookings(Long id, Long approvalStatus) {

        // Is User Valid an Already Existing?
        Entity_User user = userRepository.findById(id).get();
        if(user == null){
            return null;
        }

        // Do They Have Any Bookings?
        if(user.getBookings() == null){
            return null;
        }

        // Find Appropriate Bookings Based on Input Parameters
        Set<Entity_Booking> userBookings = new HashSet<Entity_Booking>();
        for (Entity_Booking booking :user.getBookings()) {
            if(booking.getStatus() == approvalStatus || approvalStatus == Status.getAny()){ // Return every booking if applicable.
                userBookings.add(booking);
            }
        }
        return userBookings;
    }

    private Iterable<Entity_Booking> returnUpcoming(Long id, Long approvalStatus) {
        Entity_User user = userRepository.findById(id).get();
        if(user == null){
            return null;
        }
        if(user.getBookings() == null){
            return null;
        }
        Set<Entity_Booking> userBookings = new HashSet<Entity_Booking>();
        for (Entity_Booking booking : user.getBookings()) {

            // Make Sure Only Bookings Matching Input Criteria Are Returned
            if(approvalCheck(booking, approvalStatus)){

                // Is Booking to be Run in Less than 7 Days?
                if (bookingWillRunInSevenDays(booking)) { userBookings.add(booking); }
            }
        }
        return userBookings;
    }
    
    private boolean bookingWillRunInSevenDays(Entity_Booking booking) {

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime sevenDaysFromNow = now.plusWeeks(1);
        OffsetDateTime start = booking.getStartDateTime();
        //  now -> start                  start -> sevenDays
        return start.compareTo(now) >= 0 && start.compareTo(sevenDaysFromNow) <= 0;
    }

    private boolean bookingRanLessThanSevenDaysAgo(Entity_Booking booking) {

        // Did Booking Finish Less Than 7 Days Ago?
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime sevenDaysAgo = now.minusWeeks(1);
        OffsetDateTime end = booking.getEndDateTime();
        //  sevenDaysAgo -> end                 end -> now
        return end.compareTo(sevenDaysAgo) >= 0 && end.compareTo(now) <= 0;
    }

    private Iterable<Entity_Booking> returnCompleted(Long id, Long approvalStatus) {
        Entity_User user = userRepository.findById(id).get();
        if(user == null){
            return null;
        }
        if(user.getBookings() == null){
            return null;
        }
        Set<Entity_Booking> userBookings = new HashSet<Entity_Booking>();
        for (Entity_Booking booking : user.getBookings()) {
            
            // Make Sure Only Bookings Matching Input Criteria Are Returned
            if(approvalCheck(booking, approvalStatus)){

                // Did Booking Finish Less Than 7 Days Ago?
                OffsetDateTime now = OffsetDateTime.now();
                OffsetDateTime sevenDaysAgo = now.minusWeeks(1);
                OffsetDateTime end = booking.getEndDateTime();
                //  sevenDaysAgo -> end                 end -> now
                if (end.compareTo(sevenDaysAgo) >= 0 && end.compareTo(now) <= 0) {
                    userBookings.add(booking);
                }
            }
        }
        return userBookings;
    } 

    // Helper Method
    private boolean approvalCheck(Entity_Booking booking, Long approvalStatus) {
        if(approvalStatus == Status.getApproved() && booking.getStatus() == Status.getApproved()
        || approvalStatus != Status.getApproved()) {
            return true;
        }
        return false;
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

    public Iterable<Entity_Schedule> findSchedulesByDate(Entity_User user,
                                              LocalDate date) {
        if(user.getSchedules() == null){
            return null;
        }
        // Find Schedules By Date
        Set<Entity_Schedule> userSchedules = new HashSet<Entity_Schedule>();
        for (Entity_Schedule schedule :user.getSchedules()) {

            // Does Current Schedule Occur on the Passed in Date?
            LocalDate meep = schedule.getStartDateTime().toLocalDate();
            if(meep.equals(date)) {
                userSchedules.add(schedule);
            }
        }
        return userSchedules;
    }

    public Iterable<Entity_Booking> findBookingsByDate(Entity_User user,
                                                        LocalDate date) {
        if(user.getSchedules() == null){
            return null;
        }
        // Find Schedules By Date
        Set<Entity_Booking> userBookings = new HashSet<Entity_Booking>();
        for (Entity_Booking booking :user.getBookings()) {

            // Validate What Bookings we can Retrieve
            if (user.isEmployee() && booking.isApproved() || !user.isEmployee()) {
            
                // Does Current Booking Occur on the Passed in Date?
                LocalDate curBookingsDate = booking.getStartDateTime().toLocalDate();
                if(curBookingsDate.equals(date)) {
                    userBookings.add(booking);
                }
            }
        }
        return userBookings;
    }

    public Entity_User addBookingsToUser(Entity_User user,
                                                        Iterable<Entity_Booking> bookings)
                                                        throws NullPointerException{
        if(user == null || bookings == null){
            throw new NullPointerException("users or bookings cannot be null");
        }
        //Add the bookings to the user
        for (Entity_Booking booking : bookings) {
            //Check that the user doesn't already have the bookings.
            if (user.getBookings().contains(booking) == false){
                user.getBookings().add(booking);
            }
        }

        //save the users
        return userRepository.save(user);
    }

    /**
     * Note that if you alter a booking after creating it for multiple users
     * it will update for ALL users
     * @param users
     * @param schedules
     * @return
     */
    public Iterable<Entity_User> addBookingsToUsers(Iterable<Entity_User> users,
                                                        Iterable<Entity_Booking> bookings)
                                                        throws NullPointerException{
        if(users == null || bookings == null){
            throw new NullPointerException("users or bookings cannot be null");
        }
        //Add the bookings to the users
        for (Entity_User u : users) {
            for (Entity_Booking booking : bookings) {
                //Check that the user doesn't already have the bookings.
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
        Optional<Entity_User> user = userRepository.findById(id);
        if(user.isPresent()){
            //remove any connection to services,bookings,schedules
            user.get().getBookings().clear();
            user.get().getServices().clear();
            user.get().getSchedules().clear();
            // userRepository.save(user.get());
            //Now delete
            userRepository.deleteById(id);
        }

    }
    public void deleteAll(Long[] ids){
        Iterable<Entity_User> users = this.findAllById(ids);
        if(users != null){
            for (Entity_User user : users) {
                //remove any connection to services,bookings,schedules
                user.getBookings().clear();
                user.getServices().clear();
                user.getSchedules().clear();
                // userRepository.save(user);
            }
            //Now delete
            userRepository.deleteAll(users);
        } 
    }
}