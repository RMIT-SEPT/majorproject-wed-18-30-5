package com.wed18305.assignment1.Responses;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wed18305.assignment1.model.Entity_Booking;

public class Response_Booking{

    private List<Response_Object> bookings = new ArrayList<Response_Object>();

    public Response_Booking(Iterable<Entity_Booking> bkgList)
                            throws NullPointerException{
        //Create the bookings list
        for (Entity_Booking booking : bkgList) {
            Response_Object bkg = new Response_Object(booking.getStartDateTime(),
                                                    booking.getEndDateTime(),
                                                    booking.getCustomers().iterator().next().getName(),
                                                    booking.getEmployees().iterator().next().getName(),
                                                    booking.getEmployees().iterator().next().getServices().iterator().next().getName());
            bookings.add(bkg);
        }
    } 

    //Getters
    public List<Response_Object> getBookings(){
        return this.bookings;
    }


    //Container object
    public class Response_Object{

        @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
        protected OffsetDateTime startDateTime;
        @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
        protected OffsetDateTime endDateTime;
        protected String customer;
        protected String employee;
        protected String service;
    
        public Response_Object(OffsetDateTime startDateTime,
                                OffsetDateTime endDateTime,
                                String customer,
                                String employee,
                                String service)
                                throws NullPointerException{
            //Cant pass null parameters                       
            if(startDateTime == null ||
                endDateTime == null ||
                customer == null ||
                employee == null ||
                service == null){
                    throw new NullPointerException("parameters cannot be null");
            }
            this.startDateTime = startDateTime;
            this.endDateTime = endDateTime;
            this.customer = customer;
            this.employee = employee;
            this.service = service;
        }
    
        //Getters
        public OffsetDateTime getStartDateTime(){
            return this.startDateTime;
        }
        public OffsetDateTime getEndDateTime(){
            return this.endDateTime;
        }
        public String getCustomer(){
            return this.customer;
        }
        public String getEmployee(){
            return this.employee;
        }
        public String getService(){
            return this.service;
        }
    }
}


