package com.wed18305.assignment1.Requests;

import javax.validation.constraints.NotBlank;

public class Login_Request {

    @NotBlank(message = "username is required")
    protected String username;
    @NotBlank(message = "password is required")
    protected String password;

    //Constructors
    public Login_Request(String username,
                         String password) {
        this.username = username;
        this.password = password;
    }

    //Getters
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
}