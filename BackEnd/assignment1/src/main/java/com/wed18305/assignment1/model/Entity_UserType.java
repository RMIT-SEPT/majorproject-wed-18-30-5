package com.wed18305.assignment1.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
// import javax.persistence.Table;

@Entity
// @Table(name = "userType") //Not sure if the html table creation will be needed
public class Entity_UserType {

    public static enum UserTypeID {
        ADMIN((long) 1),
        EMPLOYEE((long) 2),
        CUSTOMER((long) 3);

        public final Long id;

        UserTypeID(Long id) {
            this.id = id;
        }
    }
      

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;

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

    //Constructors
    public Entity_UserType() {
    }
    public Entity_UserType(String name) {
        this.name = name;
    }

    //Getters/Setters
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Comparisons
    @Override
    public int hashCode() {
        //Copied from http://zetcode.com/springboot/annotations/
        //change as we require
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
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
        final Entity_UserType other = (Entity_UserType) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    //String output
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserType{id=").append(id).append(", name=")
                .append(name).append("}");

        return builder.toString();
    }
}