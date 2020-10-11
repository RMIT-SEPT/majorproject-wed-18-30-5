package com.wed18305.assignment1.Requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateDetails_Request{

    @NotBlank(message = "name is required")
    protected String name;
    @NotBlank(message = "username is required")
    protected String username;
    @NotNull(message = "contact number is required")
    protected Integer contactNumber;
    protected Long employeeID;

    //Constructors
    public UpdateDetails_Request() {
    }
    public UpdateDetails_Request(String name,
                            String username,
                            Integer contactNumber,
                            Long employeeID) {
        this.name = name;
        this.username = username;
        this.contactNumber = contactNumber;
        this.employeeID = employeeID;
    }

    //Getters
    public String getName() {
        return this.name;
    }
    public String getUsername() {
        return this.username;
    }
    public Integer getContactNumber() {
        return this.contactNumber;
    }
    public Long getEmployeeID() {
        return this.employeeID;
    }
}