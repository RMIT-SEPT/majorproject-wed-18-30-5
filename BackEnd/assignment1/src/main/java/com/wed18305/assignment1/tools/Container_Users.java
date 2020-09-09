package com.wed18305.assignment1.tools;

import java.util.HashSet;
import java.util.Set;
import com.wed18305.assignment1.model.Entity_User;
import com.wed18305.assignment1.model.Entity_UserType;

public class Container_Users {
    private Set<Entity_User> users = new HashSet<Entity_User>();

    public Container_Users() {}

    public Container_Users(Set<Entity_User> users) throws NullPointerException{
        if(users == null){
            throw new NullPointerException("users cannot be null");
        }
        this.users = users;
    }
    public Container_Users(Iterable<Entity_User> users) throws NullPointerException,Exception{
        if(users == null){
            throw new NullPointerException("users cannot be null");
        }
        this.users = (Set<Entity_User>) users;
    }

    // Add new User to List of Users
    public void addUser(Entity_User user) { users.add(user); } 

    public Set<Entity_User> getUsers() { return users; }

    /**
     * 
     * @return List of Entity_User, may be empty.
     */
    public Set<Entity_User> getCustomers(){
        Set<Entity_User> customers = new HashSet<Entity_User>();
        for (Entity_User user : users) {
            if( user.getType().getId() == Entity_UserType.UserTypeID.CUSTOMER.id){
                //Customer
                customers.add(user);
            }
        }
        return customers;
    }
    /**
     * 
     * @return List of Entity_User, may be empty.
     */
    public Set<Entity_User> getEmployees(){
        Set<Entity_User> employees = new HashSet<Entity_User>();
        for (Entity_User user : users) {
            if( user.getType().getId() == Entity_UserType.UserTypeID.EMPLOYEE.id){
                //Customer
                employees.add(user);
            }
        }
        return employees;
    }
    /**
     * 
     * @return List of Entity_User, may be empty.
     */
    public Set<Entity_User> getAdmins(){
        Set<Entity_User> admins = new HashSet<Entity_User>();
        for (Entity_User user : users) {
            if( user.getType().getId() == Entity_UserType.UserTypeID.ADMIN.id){
                //Customer
                admins.add(user);
            }
        }
        return admins;
    }

}