package com.wed18305.assignment1.services;

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