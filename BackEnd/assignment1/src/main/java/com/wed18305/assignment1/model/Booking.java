package com.wed18305.assignment1.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Booking {

    /// Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    protected LocalDateTime startDateTime;
    protected LocalDateTime endDateTime;

    // Relations
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    protected User customer;

    @OneToOne
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    protected User worker;

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
                   User customer,
                   User worker) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customer = customer;
        this.worker = worker;
    }

    /// Getters/Setters
    public Long getId() { return id; }

    public LocalDateTime getStartDateTime()             { return startDateTime;    }
    public void setStartDateTime(LocalDateTime newTime) { startDateTime = newTime; }

    public LocalDateTime getEndDateTime()             { return endDateTime;    }
    public void setEndDateTime(LocalDateTime newTime) { endDateTime = newTime; }

    public User getCustomer()              { return customer;          }
    public void setCustomer(User customer) { this.customer = customer; }

    public User getWorker()            { return customer;      }
    public void setWorker(User worker) { this.worker = worker; }

    /// Comparisons
    @Override
    public int hashCode() {
        //Copied from http://zetcode.com/springboot/annotations/
        //change as we require
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final User other = (User) obj;

        /*
            Add Other Comparisons With Other Variables
        */

        return Objects.equals(this.id, other.id);
    }

    //String output
    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append("Booking{id= ").append(id).append("}");

        return builder.toString();
    }
}