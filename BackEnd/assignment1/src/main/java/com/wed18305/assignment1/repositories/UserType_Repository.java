package com.wed18305.assignment1.repositories;

import java.util.Optional;

import com.wed18305.assignment1.model.Entity_UserType;
import org.springframework.data.repository.CrudRepository;

public interface UserType_Repository extends CrudRepository<Entity_UserType, Long> {

    @Override
    Iterable<Entity_UserType> findAllById(Iterable<Long> iterable);

    Optional<Entity_UserType> findByName(String name);
}