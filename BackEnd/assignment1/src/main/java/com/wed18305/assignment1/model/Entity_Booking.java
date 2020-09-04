package com.wed18305.assignment1.model;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Entity_Booking {

    /// Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected Boolean approved = false; // Don't approve bookings by default.

    @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
    protected OffsetDateTime startDateTime;
    @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
    protected OffsetDateTime endDateTime;

    // Relations
    @ManyToMany
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    protected List<Entity_User> customers;

    @ManyToMany
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    protected List<Entity_User> employees;
    
    // Created/Updated Recordings
    @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
    private Date created_at;
    @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
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
    public Entity_Booking() {
    }
    public Entity_Booking(OffsetDateTime startDateTime,
                        OffsetDateTime endDateTime,
                        List<Entity_User> customers,
                        List<Entity_User> employees) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customers = customers;
        this.employees = employees;
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
    public OffsetDateTime getStartDateTime()             { return startDateTime;}
    public OffsetDateTime getEndDateTime()             { return endDateTime;}
    public List<Entity_User> getCustomers()              { return customers;}
    public List<Entity_User> getEmployees()              { return employees;}

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

        final Entity_Booking other = (Entity_Booking) obj;
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
        StringBuilder builder = new StringBuilder();
        builder.append("Booking{id= ").append(id).append(", startDateTime= ")
            .append(startDateTime).append(", endDateTime= ").append(endDateTime).append("}");

        return builder.toString();
    }
}