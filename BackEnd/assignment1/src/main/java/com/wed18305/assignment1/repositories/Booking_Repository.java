package com.wed18305.assignment1.repositories;

import java.util.Optional;

import com.wed18305.assignment1.model.Entity_Booking;
import org.springframework.data.repository.CrudRepository;

public interface Booking_Repository extends CrudRepository<Entity_Booking, Long> {

    @Override
    Iterable<Entity_Booking> findAllById(Iterable<Long> iterable);

    Optional<Entity_Booking> findById(Long id);

    Iterable<Entity_Booking> findAllByOrderByStartDateTime();
}
