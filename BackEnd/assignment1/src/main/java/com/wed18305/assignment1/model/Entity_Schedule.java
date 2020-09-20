package com.wed18305.assignment1.model;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Entity_Schedule {

    /// Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
    @NotNull(message = "Start date is required")
    @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
    protected OffsetDateTime startDateTime;
    @NotNull(message = "End date is required")
    @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
    protected OffsetDateTime endDateTime;
    @JsonIgnore
    @ManyToMany(mappedBy = "schedules", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected Set<Entity_User> employees = new HashSet<Entity_User>();
    @JsonIgnore
    @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
    private Date created_at;
    @JsonIgnore
    @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
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
    public Entity_Schedule() {
    }
    public Entity_Schedule(OffsetDateTime startDateTime,
                    OffsetDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /// Getters/Setters
    public Long getId() {
        return this.id; 
    }
    public OffsetDateTime getStartDateTime() {
        return this.startDateTime;
    }
    public OffsetDateTime getEndDateTime() {
        return this.endDateTime;
    }
    public Set<Entity_User> getEmployees() {
        return this.employees;
    }
    

    // Comparisons
    @Override
    public int hashCode() {
        //Copied from http://zetcode.com/springboot/annotations/
        //change as we require
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.startDateTime);
        hash = 79 * hash + Objects.hashCode(this.endDateTime);
        return hash;
    } 

    /**
     * We are going to use this to find if two schedules loosely match
     * <p>
     * Checking involves seeing if the schedule times overlap
     * <p>
     * Use hashCode if you want a direct comparison!
     */
    @Override 
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entity_Schedule other = (Entity_Schedule) obj;
        if(this.startDateTime.isAfter(other.endDateTime)){
            return false;
        }else if(this.endDateTime.isBefore(other.startDateTime)){
            return false;
        }
        //they overlap in some form
        return true;
    }

    //String output
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Schedule{id= ").append(id).append(", startDateTime= ")
            .append(startDateTime).append(", endDateTime= ").append(endDateTime).append("}");

        return builder.toString();
    }
}