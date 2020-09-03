package com.wed18305.assignment1.Requests;

// import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class Del_Request {

    protected Long object_id;

    public Del_Request(Long object_id, Long empty) { // Apparently you need 2 or more parameters for a Request to be constructed.
        this.object_id = object_id;
    }

    //Getters
    public Long getId() { return this.object_id; }
}