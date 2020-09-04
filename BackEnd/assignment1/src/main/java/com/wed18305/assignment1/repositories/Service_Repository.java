package com.wed18305.assignment1.repositories;

import java.util.Optional;

import com.wed18305.assignment1.model.Entity_Service;
import org.springframework.data.repository.CrudRepository;

public interface Service_Repository extends CrudRepository<Entity_Service, Long> {

    @Override
    Iterable<Entity_Service> findAllById(Iterable<Long> iterable);

    Optional<Entity_Service> findById(Long id);

    Optional<Entity_Service> findByName(String name);

}
