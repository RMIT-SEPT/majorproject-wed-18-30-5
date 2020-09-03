package com.wed18305.assignment1.repositories;

import java.util.Optional;

import com.wed18305.assignment1.model.Entity_Schedule;
import org.springframework.data.repository.CrudRepository;

public interface Schedule_Repository extends CrudRepository<Entity_Schedule, Long> {

    @Override
    Iterable<Entity_Schedule> findAllById(Iterable<Long> iterable);

    Optional<Entity_Schedule> findById(Long id);
}
