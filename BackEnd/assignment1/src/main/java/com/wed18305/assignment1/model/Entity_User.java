package com.wed18305.assignment1.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wed18305.assignment1.model.Entity_UserType.UserTypeID;

//Relation example: https://www.baeldung.com/jpa-one-to-one

@Entity
public class Entity_User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
    protected String name;
    @Column(unique = true)
    protected String username;
    @JsonIgnore
    protected String password;
    protected String contactNumber;
    protected String address;
    @OneToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    protected Entity_UserType userType;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_schedules", 
                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
                inverseJoinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id"))
    protected Set<Entity_Schedule> schedules = new HashSet<Entity_Schedule>();
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_services", 
                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
                inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    protected Set<Entity_Service> services = new HashSet<Entity_Service>();
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_bookings", 
                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
                inverseJoinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "id"))
    protected Set<Entity_Booking> bookings = new HashSet<Entity_Booking>();
    

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

    //Constructors
    public Entity_User() {
    }
    public Entity_User(String name,
                    String username,
                    String password,
                    String contactNumber,
                    String address,
                    Entity_UserType type) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.contactNumber = contactNumber;
        this.address = address;
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
    public String getAddressNumber() {
        return this.address;
    }
    public void setAddressNumber(String address) {
        this.address = address;
    }
    // What Type is the User?
    public Entity_UserType getType() {
        return this.userType;
    }

    public boolean isAdmin()    { return this.getType().getId() == UserTypeID.ADMIN.id;    }
    public boolean isEmployee() { return this.getType().getId() == UserTypeID.EMPLOYEE.id; }
    public boolean isCustomer() { return this.getType().getId() == UserTypeID.CUSTOMER.id; }


    /**
     * Only call this from User_service, standard procedure below
     * <p>
     * .getServices().add()
     * <p>
     * .getServices().remove()
     * <p>
     * After adding removing you must save the updated user
     * <p>
     * User_service.saveOrUpdateUser(user)
     * @return List<Entity_Service>
     */
    public Set<Entity_Service> getServices() {
        return this.services;
    }
    /**
     * Only call this from User_service, standard procedure below
     * <p>
     * .getSchedules().add()
     * <p>
     * .getSchedules().remove()
     * <p>
     * After adding removing you must save the updated user
     * <p>
     * User_service.saveOrUpdateUser(user)
     * @return List<Schedule>
     */
    public Set<Entity_Schedule> getSchedules() {
        return this.schedules;
    }
    /**
     * Only call this from User_service, standard procedure below
     * <p>
     * .getBookings().add()
     * <p>
     * .getBookings().remove()
     * <p>
     * After adding removing you must save the updated user
     * <p>
     * User_service.saveOrUpdateUser(user)
     * @return List<Schedule>
     */
    public Set<Entity_Booking> getBookings() {
        return this.bookings;
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
        final Entity_User other = (Entity_User) obj;
        if (!Objects.equals(this.userType, other.userType)) {
            return false;
        }
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
        StringBuilder builder = new StringBuilder();
        builder.append("User{id=").append(id).append(", name=")
                .append(name).append("}");

        return builder.toString();
    }
}