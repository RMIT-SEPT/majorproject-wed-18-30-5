package com.wed18305.assignment1.Requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class User_Request {

    @NotBlank(message = "name is required")
    protected String name;
    @NotBlank(message = "username is required")
    protected String username;
    @NotBlank(message = "password is required")
    protected String password;
    @NotNull(message = "contact number is required")
    protected Integer contactNumber;
    @NotNull(message = "request ID is required")
    protected Long requestID;

    //Constructors
    public User_Request(String name,
                         String username,
                         String password,
                         Integer contactNumber,
                         Long requestID) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.contactNumber = contactNumber;
        this.requestID = requestID;
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
    public Integer getContactNumber() {
        return this.contactNumber;
    }
    public Long getRequestID() {
        return requestID;
    }
}