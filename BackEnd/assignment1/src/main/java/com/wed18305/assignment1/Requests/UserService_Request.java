package com.wed18305.assignment1.Requests;

import javax.validation.constraints.NotNull;

public class UserService_Request {

    @NotNull(message = "request ID is required")
    protected Long service_id;

    //Constructors
    public UserService_Request(Long service_id, Long empty) {
        this.service_id = service_id;
    }

    //Getters
    public Long getServiceID() {
        return service_id;
    }
}