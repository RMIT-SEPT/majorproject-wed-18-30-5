package com.wed18305.assignment1.Requests;

// import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class Delete_Request{

    @NotEmpty(message = "username is required")
    protected String[] username;


    public Delete_Request(String[] username, String empty) {
        this.username = username;
    }

    //Getters
    public String[] getUsername() {
        return this.username;
    }
}