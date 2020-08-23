package com.wed18305.assignment1.Requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Service_Request {

    //// Variables
    @NotBlank(message = "Please provide a name.")
    protected String name;
    @NotNull(message = "request ID is required.")
    protected Long requestID;

    //// Constructor
    public Service_Request(String name, Long requestID) {
        this.name = name;
        this.requestID = requestID;
    }

    //// Getters
    public String getName()    { return name;      }
    public Long getRequestID() { return requestID; }
}