package com.wed18305.assignment1.repositories;

import java.util.Optional;

import com.wed18305.assignment1.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface Booking_Repository extends CrudRepository<Booking, Long> {

    @Override
    Iterable<Booking> findAllById(Iterable<Long> iterable);

    Optional<Booking> findById(Long id);
}
