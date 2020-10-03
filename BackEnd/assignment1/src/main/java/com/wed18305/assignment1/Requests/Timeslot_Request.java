package com.wed18305.assignment1.Requests;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.wed18305.assignment1.config.DateTimeStatic;

public class Timeslot_Request{

    @NotNull(message = "date is required")
    protected OffsetDateTime dateTime;
    @NotNull(message = "id is required")
    protected Long userID;

    //Constructors
    public Timeslot_Request(String dateTime,
                            Long userID) {
        this.dateTime = OffsetDateTime.parse(dateTime, DateTimeStatic.getFormatter());
        this.userID = userID;
    }

    //Getters
    public OffsetDateTime getDateTime() {
        return this.dateTime;
    }
    public Long getUserID() {
        return this.userID;
    }
}