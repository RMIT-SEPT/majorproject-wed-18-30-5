package com.wed18305.assignment1.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Booking {

    /// Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected ZonedDateTime startDateTime;
    protected ZonedDateTime endDateTime;

    // Relations
    @ManyToMany
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    protected List<User_model> customer;

    @ManyToMany
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    protected List<User_model> employee;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    protected Service service;

    protected Boolean approved = false; // Don't approve bookings by default.
    
    // Created/Updated Recordings
    private Date created_at;
    private Date updated_at;

    // PrePersist: Do before initializing a record.
    @PrePersist
    protected void onCreate(){
        this.created_at = new Date();
    }

    // PreUpdate: Do before updating a record.
    @PreUpdate
    protected void onUpdate(){
        this.updated_at = new Date();
    }

    /// Constructor
    public Booking() {
    }
    public Booking(ZonedDateTime startDateTime,
                   ZonedDateTime endDateTime,
                   List<User_model> customer,
                   List<User_model> employee,
                   Service service) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customer = customer;
        this.employee = employee;
        this.service = service;
    }

    

    /// Getters/Setters

    /*
        Fun fact: Getter's are used by Springboot to display JSON object data.
        I forgot to include a getter for the 'service' variable, and thus, the returned JSON excluded that data.
        Even if you don't call a get method, Springboot automatically calls it for you to printout the data.
        Neat!
    */

    // Whatever the getter's name is, that'll be used to represent a section of data in a JSON object.

    public Long getId() { return id; }

    public ZonedDateTime getStartDateTime()             { return startDateTime;    }
    public void setStartDateTime(ZonedDateTime newTime) { startDateTime = newTime; }

    public ZonedDateTime getEndDateTime()             { return endDateTime;    }
    public void setEndDateTime(ZonedDateTime newTime) { endDateTime = newTime; }

    public List<User_model> getCustomers()              { return customer;          }
    public void setCustomers(List<User_model> customer) { this.customer = customer; }

    public List<User_model> getEmployees()              { return customer;          }
    public void setEmployees(List<User_model> employee) { this.employee = employee; }

    public Service getService()                 { return service;           }
    public void setSerivce(Service service)     { this.service = service;   }

    public Boolean getApproved() { return approved; }
    public void approveBooking() { approved = true; }

    /// Comparisons
    @Override
    public int hashCode() {
        //Copied from http://zetcode.com/springboot/annotations/
        //change as we require
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.startDateTime);
        hash = 79 * hash + Objects.hashCode(this.endDateTime);
        return hash;
    } 

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Booking other = (Booking) obj;
        if (!Objects.equals(this.startDateTime, other.startDateTime)) {
            return false;
        }
        if (!Objects.equals(this.endDateTime, other.endDateTime)) {
            return false;
        }

        return Objects.equals(this.id, other.id);
    }

    //String output
    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append("Booking{id= ").append(id).append(", startDateTime= ")
            .append(startDateTime).append(", endDateTime= ").append(endDateTime).append("}");

        return builder.toString();
    }
}