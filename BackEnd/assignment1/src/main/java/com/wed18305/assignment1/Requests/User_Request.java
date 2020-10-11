package com.wed18305.assignment1.Requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class User_Request{

    @NotBlank(message = "name is required")
    protected String name;
    @NotBlank(message = "username is required")
    protected String username;
    @NotBlank(message = "password is required")
    protected String password;
    @NotNull(message = "address is required")
    protected String address;
    @NotNull(message = "contact number is required")
    protected Integer contactNumber;

    //Constructors
    public User_Request() {
    }
    public User_Request(String name,
                            String username,
                            String password,
                            String address,
                            Integer contactNumber) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    //Getters
    public String getName() {
        return this.name;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public String getAddress() {
        return this.address;
    }
    public Integer getContactNumber() {
        return this.contactNumber;
    }
}