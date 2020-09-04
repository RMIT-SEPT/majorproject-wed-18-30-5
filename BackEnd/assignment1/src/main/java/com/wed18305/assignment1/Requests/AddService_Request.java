package com.wed18305.assignment1.Requests;

//import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddService_Request {

    // Variables
    @NotNull(message = "Please provide a service name.")
    protected Long[] service_ids;
    @NotNull(message = "Please provide a user ids.")
    protected Long[] user_ids;

    // Constructor
    public AddService_Request(Long[] service_ids, Long[] user_ids) {
        this.service_ids = service_ids;
        this.user_ids = user_ids;
    }

    // Getters
    public Long[] getServiceIds() { 
        return this.service_ids;
    }
    public Long[] getUserIds() { 
        return this.user_ids;
    }
}