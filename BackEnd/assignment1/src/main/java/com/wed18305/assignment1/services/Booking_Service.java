package com.wed18305.assignment1.services;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Optional;

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

            // Is Booking to be Run in Less than 7 Days?
            OffsetDateTime now = OffsetDateTime.now();
            OffsetDateTime sevenDaysFromNow = now.plusWeeks(1);
            OffsetDateTime start = booking.getStartDateTime();
            //  now -> start                  start -> sevenDays
            if (start.compareTo(now) >= 0 && start.compareTo(sevenDaysFromNow) <= 0) {
                bookings.add(booking);
            }
        }

        return bookings;
    } 

    public Iterable<Entity_Booking> findAllCompleted() {

        ArrayList<Entity_Booking> bookings = new ArrayList<>();

        for (Entity_Booking booking : bookingRepository.findAll()) {

            // Did Booking Finish Less Than 7 Days Ago?
            OffsetDateTime now = OffsetDateTime.now();
            OffsetDateTime sevenDaysAgo = now.minusWeeks(1);
            OffsetDateTime end = booking.getEndDateTime();
            //  sevenDaysAgo -> end                 end -> now
            if (end.compareTo(sevenDaysAgo) >= 0 && end.compareTo(now) <= 0) {
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