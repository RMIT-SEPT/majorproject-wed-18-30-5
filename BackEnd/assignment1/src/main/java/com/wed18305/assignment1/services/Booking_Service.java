package com.wed18305.assignment1.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javax.persistence.Entity;

import com.wed18305.assignment1.model.Entity_Booking;
import com.wed18305.assignment1.repositories.Booking_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Booking_Service {
    @Autowired
    private Booking_Repository bookingRepository;

    public Entity_Booking saveOrUpdateBooking(Entity_Booking Booking) {
        return bookingRepository.save(Booking);
    }

    public Iterable<Entity_Booking> findAll() {
		return bookingRepository.findAll();
    }
    
    public Iterable<Entity_Booking> findAllUpcoming() {

        ArrayList<Entity_Booking> bookings = new ArrayList<>();

        for (Entity_Booking booking : bookingRepository.findAll()) {

            // Booking hasn't Occurred, or Finished Yet
            if (LocalDateTime.now().compareTo(booking.getEndDateTime()) < 0) {
                bookings.add(booking);
            }
        }

        return bookings;
    } 

    public Iterable<Entity_Booking> findAllCompleted() {

        ArrayList<Entity_Booking> bookings = new ArrayList<>();

        for (Entity_Booking booking : bookingRepository.findAll()) {

            // Booking has Occured
            if (LocalDateTime.now().compareTo(booking.getEndDateTime()) > 0) {
                bookings.add(booking);
            }
        }

        return bookings;
    }

    public Optional<Entity_Booking> findById(Long id){
        return bookingRepository.findById(id);
    }

    public void deleteManyById(Long[] ids){
        for (Long id : ids) {
            bookingRepository.deleteById(id);
        }
    }

    public void deleteById(){
        bookingRepository.deleteById(Long.parseLong("1"));
    }

}