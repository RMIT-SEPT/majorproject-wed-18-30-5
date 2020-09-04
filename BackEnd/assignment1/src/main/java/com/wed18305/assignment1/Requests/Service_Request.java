package com.wed18305.assignment1.Requests;

import javax.validation.constraints.NotBlank;

public class Service_Request {

    //// Variables
    @NotBlank(message = "Please provide a name.")
    protected String name;

    //// Constructor
    public Service_Request(String name, String empty) {
        this.name = name;
    }

    //// Getters
    public String getName()    { return name;}
}