package com.wed18305.assignment1.model;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity // Add this to make the class a managed type.
public class Entity_Service {

    //// Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    protected String name;
    protected Integer length = 30; //Default time length is 30(minutes)

    // Datetime
    @JsonIgnore
    @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
    private Date created_at;
    @JsonIgnore
    @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
    private Date updated_at;

    // Set created_at Value when Object's Created
    @PrePersist
    protected void onCreate() { this.created_at = new Date(); }

    // Set updated_at Value when Object's Updated
    @PreUpdate
    protected void onUpdate() { this.updated_at = new Date(); }

    //// Constructor
    public Entity_Service() {}
    /**
     * This constructor uses the default service length of 30mins
     * @param name
     */
    public Entity_Service(String name) {
        this.name = name;
    }
    public Entity_Service(String name,Integer length) {
        this.name = name;
        this.length= length;
    }

    //// Getters/Setters
    public long getId() { return id; }

    public String getName()          { return name;      }
    public void setName(String name) { this.name = name; }
    public Integer getLength(){
        return this.length;
    }

    /// Comparisons
    @Override
    public int hashCode() {
        //Copied from http://zetcode.com/springboot/annotations/
        //change as we require
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.length);
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
        final Entity_Service other = (Entity_Service) obj;

        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.length, other.length)) {
            return false;
        }

        return Objects.equals(this.id, other.id);
    }

    //String output
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Booking{id= ").append(id).append(", name= ").append(name).append("}");

        return builder.toString();
    }
}