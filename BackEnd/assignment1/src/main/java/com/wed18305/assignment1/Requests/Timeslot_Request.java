package com.wed18305.assignment1.Requests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.wed18305.assignment1.config.DateTimeStatic;

public class Timeslot_Request{

    @NotNull(message = "date is required")
    protected LocalDate date;
    @NotNull(message = "id is required")
    protected Long userID;

    //Constructors
    public Timeslot_Request(String date,
                            Long userID) {
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd"));
        this.userID = userID;
    }

    //Getters
    public LocalDate getDate() {
        return this.date;
    }
    public Long getUserID() {
        return this.userID;
    }
}