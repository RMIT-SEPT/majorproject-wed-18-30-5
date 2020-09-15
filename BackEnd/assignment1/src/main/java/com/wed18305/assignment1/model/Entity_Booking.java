package com.wed18305.assignment1.model;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Entity_Booking {

    public static enum ApprovalStatus {
        ANY((long) 0), // Debug Value, for finding bookings with any approvalStatus.
        PENDING((long) 1),
        APPROVED((long) 2),
        DENIED((long) 3);

        public final Long id;
        ApprovalStatus(Long id) {
            this.id = id;
        }
        public static Long getAny()      { return ANY.id;      }
        public static Long getPending()  { return PENDING.id;  }
        public static Long getApproved() { return APPROVED.id; }
        public static Long getDenied()   { return DENIED.id;   }
    }

    /// Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    // protected Boolean approved = false; // Don't approve bookings by default.
    protected ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
    protected OffsetDateTime startDateTime;
    @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
    protected OffsetDateTime endDateTime;

    //Relations
    // @JsonIgnore
    // @ManyToMany
    // @JoinColumn(name = "customer_id", referencedColumnName = "id")
    // protected List<Entity_User> customers;

    // @JsonIgnore
    // @ManyToMany
    // @JoinColumn(name = "employee_id", referencedColumnName = "id")
    // protected List<Entity_User> employees;

    @JsonIgnore
    @ManyToMany(mappedBy = "bookings", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected Set<Entity_User> users  = new HashSet<Entity_User>();
    
    // Created/Updated Recordings
    @JsonIgnore
    @JsonFormat(pattern="yyyy-MM-dd@HH:mm")
    private Date created_at;
    @JsonIgnore
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
    // public Entity_Booking(OffsetDateTime startDateTime,
    //                     OffsetDateTime endDateTime,
    //                     List<Entity_User> customers,
    //                     List<Entity_User> employees) {
    //     this.startDateTime = startDateTime;
    //     this.endDateTime = endDateTime;
    //     this.customers = customers;
    //     this.employees = employees;
    // }
    public Entity_Booking(OffsetDateTime startDateTime,
                        OffsetDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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
    // public List<Entity_User> getCustomers()              { return customers;}
    // public List<Entity_User> getEmployees()              { return employees;}
    public Set<Entity_User> getUsers()              { return users;}
    @JsonIgnore
    /**
     * 
     * @return List of Entity_User, may be empty.
     */
    public Set<Entity_User> getCustomers(){
        Set<Entity_User> customers = new HashSet<Entity_User>();
        for (Entity_User user : users) {
            if( user.getType().getId() == Entity_UserType.UserTypeID.CUSTOMER.id){
                //Customer
                customers.add(user);
            }
        }
        return customers;
    }
    @JsonIgnore
    /**
     * 
     * @return List of Entity_User, may be empty.
     */
    public Set<Entity_User> getEmployees(){
        Set<Entity_User> employees = new HashSet<Entity_User>();
        for (Entity_User user : users) {
            if( user.getType().getId() == Entity_UserType.UserTypeID.EMPLOYEE.id){
                //Customer
                employees.add(user);
            }
        }
        return employees;
    }

    public long getApprovalStatus() { return approvalStatus.id;                 }
    public void approveBooking()    { approvalStatus = ApprovalStatus.APPROVED; }
    public void denyBooking()       { approvalStatus = ApprovalStatus.DENIED;   }

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