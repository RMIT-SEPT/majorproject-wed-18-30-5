package com.wed18305.assignment1.Requests;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Booking_Request {

    /// Variables
    // @NotBlank(message = "A StartDate is required.")
    protected LocalDateTime startDateTime;
    // @NotBlank(message = "A EndDate is required.")
    protected LocalDateTime endDateTime;
    @NotNull(message = "request ID is required.")
    protected Long requestID;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // "Day, Month, Year, Hour, Minute."
    
    //Constructors
    public Booking_Request(String startDateTime,
                           String endDateTime,
                           Long requestID) {
        this.startDateTime = LocalDateTime.parse(startDateTime, formatter);
        this.endDateTime   = LocalDateTime.parse(endDateTime, formatter);
        this.requestID     = requestID;
    }

    // Getters
    public LocalDateTime getStartDate() { return startDateTime; }
    public LocalDateTime getEndDate()   { return endDateTime;   }
    public Long getRequestID() { return requestID;     }
}