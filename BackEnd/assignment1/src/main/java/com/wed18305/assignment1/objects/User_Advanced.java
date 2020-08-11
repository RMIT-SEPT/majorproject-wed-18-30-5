package com.wed18305.assignment1.objects;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.wed18305.assignment1.model.User;

@Entity
public class User_Advanced extends User{

    @NotNull(message = "requestID is required")
    private Long requestID;
    /**
     * requestID is the UserID that has requested to create an employee.
     * @return the requestID
     */
    public Long getRequestID() {
        return requestID;
    }

    //Constructors
    public User_Advanced(String name,
                String username,
                String password,
                String contactNumber,
                Long requestID) {
        super(name,username,password,contactNumber,null);
        // this.name = name;
        // this.username = username;
        // this.password = password;
        // this.contactNumber = contactNumber;
        this.requestID = requestID;
        // this.type = typeID;
    }
}