package com.wed18305.assignment1.Requests;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.wed18305.assignment1.model.User;
import com.wed18305.assignment1.services.User_Service;

public class Booking_Request {

    /// Variables
    // @NotBlank(message = "A StartDate is required.")
    protected LocalDateTime startDateTime;
    // @NotBlank(message = "A EndDate is required.")
    protected LocalDateTime endDateTime;
    // @NotBlank(message = "Please assign a customer to this booking.")
    protected long customer_id;
    // @NotBlank(message = "Please assign a worker to this booking.")
    protected long worker_id;
    @NotNull(message = "request ID is required.")
    protected Long requestID;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // "Day, Month, Year, Hour, Minute."
    
    //Constructors
    public Booking_Request(String startDateTime,
                           String endDateTime,
                           long customer_id,
                           long worker_id,
                           Long requestID) {
        this.startDateTime = LocalDateTime.parse(startDateTime, formatter);
        this.endDateTime   = LocalDateTime.parse(endDateTime, formatter);
        this.customer_id   = customer_id;
        this.worker_id     = worker_id;
        this.requestID     = requestID;
    }

    // Getters
    public LocalDateTime getStartDate()   { return startDateTime;    }
    public LocalDateTime getEndDate()     { return endDateTime;      }
    public Long getCustomerId()           { return customer_id;      }
    public Long getWorkerId()             { return worker_id;        }
    public Long getRequestID()            { return requestID;        }
}