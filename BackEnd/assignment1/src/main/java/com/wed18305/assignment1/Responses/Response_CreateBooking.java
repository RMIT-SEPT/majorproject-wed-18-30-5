package com.wed18305.assignment1.Responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wed18305.assignment1.model.Entity_Booking;

public class Response_CreateBooking{//TODO need to handle the arrays better in the constructor

    private List<Response_Object> bookings = new ArrayList<Response_Object>();

    public Response_CreateBooking(Entity_Booking booking) {

        // Add Only 1 Booking
        Response_Object bkg = new Response_Object(
                                                    booking.getStartDateTime().toLocalDateTime(),
                                                    booking.getEndDateTime().toLocalDateTime(),
                                                    booking.getStatusName(),
                                                    booking.getId());
        bookings.add(bkg);
    }

    //Getters
    public List<Response_Object> getBookings(){
        return this.bookings;
    }

    //Container object
    public class Response_Object{

        @JsonFormat(pattern = "yyyy-MM-dd")
        protected LocalDate date;
        @JsonFormat(pattern="HH:mm a") // Hour:Minute AM/Pm
        protected LocalDateTime startTime;
        @JsonFormat(pattern="HH:mm a")
        protected LocalDateTime endTime;
        protected String customer;
        protected String employee;
        protected String service;
        protected String status;
        protected Long bookingID;
    
        public Response_Object( LocalDateTime startDateTime,
                                LocalDateTime endDateTime,
                                String status,
                                Long bookingID)
                                throws NullPointerException{
            //Cant pass null parameters                       
            if( startDateTime == null ||
                endDateTime == null ||
                status  == null ||
                bookingID == null){
                    throw new NullPointerException("parameters cannot be null");
            }
            this.date = startDateTime.toLocalDate();
            this.startTime = startDateTime;
            this.endTime = endDateTime;
            this.status = status;
            this.bookingID = bookingID;
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
        public String getStatus(){
            return this.status;
        }
        public Long getBookingID(){
            return this.bookingID;
        }
    }
}