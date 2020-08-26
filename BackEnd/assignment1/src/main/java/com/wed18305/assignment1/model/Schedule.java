package com.wed18305.assignment1.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

@Entity
public class Schedule {

    /// Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
    @NotNull(message = "Start date is required")
    protected LocalDateTime startDateTime;
    @NotNull(message = "End date is required")
    protected LocalDateTime endDateTime;
    @ManyToMany()
    @JoinTable(name = "user_schedules", 
            joinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id"), 
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    protected List<User_model> employees = new ArrayList<User_model>();
    
    private Date created_at;
    private Date updated_at;
    @PrePersist
    protected void onCreate(){
        this.created_at = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updated_at = new Date();
    }

    // Constructor
    public Schedule() {
    }
    public Schedule(LocalDateTime startDateTime,
                   LocalDateTime endDateTime,
                   List<User_model> employees) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.employees = employees;
    }

    /// Getters/Setters
    public Long getId() {
        return this.id; 
    }
    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }
    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }
    public List<User_model> getEmployees() {
        return this.employees;
    }
    

    // Comparisons
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
        builder.append("Schedule{id= ").append(id).append(", startDateTime= ")
            .append(startDateTime).append(", endDateTime= ").append(endDateTime).append("}");

        return builder.toString();
    }
}