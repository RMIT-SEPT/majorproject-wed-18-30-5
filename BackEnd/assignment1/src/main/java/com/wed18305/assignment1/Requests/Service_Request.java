package com.wed18305.assignment1.Requests;

import javax.validation.constraints.NotBlank;

public class Service_Request {

    //// Variables
    @NotBlank(message = "Please provide a name.")
    protected String name;
    protected Integer length;

    //// Constructor
    public Service_Request(String name, Integer length) {
        this.name = name;
        this.length = length;
    }

    //// Getters
    public String getName()    { return name;}
    public Integer getLength(){
        return this.length;
    }
}