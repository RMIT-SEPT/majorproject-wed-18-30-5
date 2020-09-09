package com.wed18305.assignment1.Requests;

import java.time.OffsetDateTime;
import javax.validation.constraints.NotNull;
import com.wed18305.assignment1.config.DateTimeStatic;

public class Schedule_Request {

    /// Variables
    @NotNull(message = "A StartDate is required.")
    protected OffsetDateTime startDateTime;
    @NotNull(message = "A EndDate is required.")
    protected OffsetDateTime endDateTime;
    @NotNull(message = "Please assign a worker to this booking.")
    protected Long[] user_ids;
    
    // Constructors
    public Schedule_Request(String startDateTime,
                           String endDateTime,
                           Long[] user_ids) {
        this.startDateTime  = OffsetDateTime.parse(startDateTime, DateTimeStatic.getFormatter());
        this.endDateTime    = OffsetDateTime.parse(endDateTime, DateTimeStatic.getFormatter());
        this.user_ids   = user_ids;
    }

    // Getters
    public OffsetDateTime getStartDate() {
        return startDateTime;
    }
    public OffsetDateTime getEndDate() {
        return endDateTime;
    }
    public Long[] getUserIds() {
        return user_ids;
    }
}