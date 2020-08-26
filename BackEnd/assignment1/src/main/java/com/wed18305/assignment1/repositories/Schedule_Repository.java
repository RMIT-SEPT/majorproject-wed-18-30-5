package com.wed18305.assignment1.repositories;

import java.util.Optional;

import com.wed18305.assignment1.model.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface Schedule_Repository extends CrudRepository<Schedule, Long> {

    @Override
    Iterable<Schedule> findAllById(Iterable<Long> iterable);

    Optional<Schedule> findById(Long id);
}
