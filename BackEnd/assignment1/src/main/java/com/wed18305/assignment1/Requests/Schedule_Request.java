package com.wed18305.assignment1.Requests;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.wed18305.assignment1.config.DateTimeStatic;

public class Schedule_Request {

    /// Variables
    @NotNull(message = "A StartDate is required.")
    protected LocalDateTime startDateTime;
    @NotNull(message = "A EndDate is required.")
    protected LocalDateTime endDateTime;
    @NotNull(message = "Please assign a worker to this booking.")
    protected Long[] user_ids;
    
    // Constructors
    public Schedule_Request(String startDateTime,
                           String endDateTime,
                           Long[] user_ids) {
        this.startDateTime  = LocalDateTime.parse(startDateTime, DateTimeStatic.getFormatter());
        this.endDateTime    = LocalDateTime.parse(endDateTime, DateTimeStatic.getFormatter());
        this.user_ids   = user_ids;
    }

    // Getters
    public LocalDateTime getStartDate() {
        return startDateTime;
    }
    public LocalDateTime getEndDate() {
        return endDateTime;
    }
    public Long[] getUserIds() {
        return user_ids;
    }
}