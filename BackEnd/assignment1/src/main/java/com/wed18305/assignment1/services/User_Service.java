package com.wed18305.assignment1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wed18305.assignment1.model.Entity_Service;
import com.wed18305.assignment1.model.Entity_Schedule;
import com.wed18305.assignment1.model.Entity_User;
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