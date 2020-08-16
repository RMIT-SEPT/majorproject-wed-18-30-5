package com.wed18305.assignment1.services;

import java.util.Optional;

import com.wed18305.assignment1.model.Booking;
import com.wed18305.assignment1.repositories.Booking_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Booking_Service {
    @Autowired
    private Booking_Repository BookingRepository;

    public Booking saveOrUpdateBooking(Booking Booking) {
        return BookingRepository.save(Booking);
    }

    public Optional<Booking> findById(Long id){
        return BookingRepository.findById(id);
    }
}