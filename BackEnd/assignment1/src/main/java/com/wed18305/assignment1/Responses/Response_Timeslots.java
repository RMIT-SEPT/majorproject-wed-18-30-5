package com.wed18305.assignment1.Responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wed18305.assignment1.model.Entity_Booking;

public class Response_Timeslots{//TODO need to handle the arrays better in the constructor

    private List<Response_Object> bookedTimes = new ArrayList<Response_Object>();

    public Response_Timeslots(Iterable<Entity_Booking> bookList)
                            throws NullPointerException{
        //Create the bookings list
        for (Entity_Booking booking : bookList) {
            
            Response_Object resBook = new Response_Object(
                                                    booking.getId(),
                                                    booking.getStartDateTime().toLocalDateTime(),
                                                    booking.getEndDateTime().toLocalDateTime());
            bookedTimes.add(resBook);
        }
    }

    //Getters
    public List<Response_Object> getBookedTimes(){
        return this.bookedTimes;
    }

    //Container object
    public class Response_Object{

        @JsonFormat(pattern = "yyyy-MM-dd")
        protected LocalDate date;
        @JsonFormat(pattern="HH:mm a") // Hour:Minute AM/Pm
        protected LocalDateTime startTime;
        @JsonFormat(pattern="HH:mm a")
        protected LocalDateTime endTime;
        protected Long bookingID;
    
        public Response_Object( Long bookingID,
                                LocalDateTime startDateTime,
                                LocalDateTime endDateTime
                                )
                                throws NullPointerException{
            //Cant pass null parameters                       
            if( bookingID == null ||
                startDateTime == null ||
                endDateTime == null){
                    throw new NullPointerException("parameters cannot be null");
            }
            this.bookingID = bookingID;
            this.date = startDateTime.toLocalDate();
            this.startTime = startDateTime;
            this.endTime = endDateTime;
        }
    
        //Getters
        public LocalDate getDate() {
            return this.date;
        }
        public LocalDateTime getStartTime(){
            return this.startTime;
        }
        public LocalDateTime getEndTime(){
            return this.endTime;
        }
        public Long getBookingID(){
            return this.bookingID;
        }
    }
}