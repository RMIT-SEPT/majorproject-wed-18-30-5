package com.wed18305.assignment1.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

    protected LocalDateTime startDateTime;
    protected LocalDateTime endDateTime;

    // Relations
    @OneToMany
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    protected List<User> customer;

    @OneToMany
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    protected List<User> employee;

    // @OneToOne
    // @JoinColumn(name = "service_id", referencedColumnName = "id")
    // protected Service service;
    
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
    public Booking(LocalDateTime startDateTime,
                   LocalDateTime endDateTime,
                   List<User> customer,
                   List<User> employee) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customer = customer;
        this.employee = employee;
    }

    /// Getters/Setters
    public Long getId() { return id; }

    public LocalDateTime getStartDateTime()             { return startDateTime;    }
    public void setStartDateTime(LocalDateTime newTime) { startDateTime = newTime; }

    public LocalDateTime getEndDateTime()             { return endDateTime;    }
    public void setEndDateTime(LocalDateTime newTime) { endDateTime = newTime; }

    public List<User> getCustomer()              { return customer;          }
    public void setCustomer(List<User> customer) { this.customer = customer; }

    public List<User> getWorker()            { return customer;        }
    public void setWorker(List<User> worker) { this.employee = worker; }

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