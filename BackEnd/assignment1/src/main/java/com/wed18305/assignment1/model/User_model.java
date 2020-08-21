package com.wed18305.assignment1.model;

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
// import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
// import org.hibernate.validator.constraints.NotBlank;

//Relation example: https://www.baeldung.com/jpa-one-to-one

@Entity
// @Table(name = "users") //Not sure if the html table creation will be needed
public class User_model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    @NotBlank(message = "username is required")
    protected String username;
    @NotBlank(message = "password is required")
    protected String password;
    protected String contactNumber;
    @OneToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    protected UserType userType;
    protected String services; //Optional, not sure if we need a list of services here.

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
    public User_model() {
    }
    public User_model(String name,
                String username,
                String password,
                String contactNumber,
                UserType type) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.contactNumber = contactNumber;
        this.userType = type;
    }

    //Getters/Setters
    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getContactNumber() {
        return this.contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public UserType getType() {
        return this.userType;
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
        final User_model other = (User_model) obj;
        // if (!Objects.equals(this.type, other.type)) {
        //     return false;
        // }
        if (!Objects.equals(this.contactNumber, other.contactNumber)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    //String output
    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append("User{id=").append(id).append(", name=")
                .append(name).append("}");

        return builder.toString();
    }
}