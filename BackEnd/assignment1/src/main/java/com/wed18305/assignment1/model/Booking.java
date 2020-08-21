package com.wed18305.assignment1.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Booking {

    /// Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    protected Date startDateTime;
    protected Date endDateTime;
    
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
    public Booking(Date startDateTime,
                Date endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /// Getters/Setters
    public Long getId() { return id; }

    public Date getStartDateTime()             { return startDateTime; }
    public void setStartDateTime(Date newTime) { startDateTime = newTime; }

    public Date getEndDateTime()             { return endDateTime; }
    public void setEndDateTime(Date newTime) { endDateTime = newTime; }

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
        final User_model other = (User_model) obj;

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