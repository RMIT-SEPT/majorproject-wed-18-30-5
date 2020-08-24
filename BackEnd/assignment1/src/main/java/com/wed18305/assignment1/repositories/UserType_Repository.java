package com.wed18305.assignment1.repositories;

import com.wed18305.assignment1.model.UserType;
import org.springframework.data.repository.CrudRepository;

public interface UserType_Repository extends CrudRepository<UserType, Long> {

    @Override
    Iterable<UserType> findAllById(Iterable<Long> iterable);
}