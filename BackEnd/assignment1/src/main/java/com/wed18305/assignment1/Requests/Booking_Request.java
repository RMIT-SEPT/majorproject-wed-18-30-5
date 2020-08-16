package com.wed18305.assignment1.Requests;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Booking_Request {

    /// Variables
    // @NotBlank(message = "A StartDate is required.")
    protected Date startDateTime;
    // @NotBlank(message = "A EndDate is required.")
    protected Date endDateTime;
    @NotNull(message = "request ID is required.")
    protected Long requestID;
    
    //Constructors
    public Booking_Request(Date startDateTime,
                         Date endDateTime,
                         Long requestID) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.requestID = requestID;
    }

    // Getters
    public Date getStartDate() { return startDateTime; }
    public Date getEndDate()   { return endDateTime;   }
    public Long getRequestID() { return requestID;     }
}