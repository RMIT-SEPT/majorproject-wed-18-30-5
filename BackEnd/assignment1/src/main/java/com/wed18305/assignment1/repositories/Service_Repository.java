package com.wed18305.assignment1.repositories;

import java.util.Optional;

import com.wed18305.assignment1.model.Service;
import org.springframework.data.repository.CrudRepository;

public interface Service_Repository extends CrudRepository<Service, Long> {

    @Override
    Iterable<Service> findAllById(Iterable<Long> iterable);

    Optional<Service> findById(Long id);
}
