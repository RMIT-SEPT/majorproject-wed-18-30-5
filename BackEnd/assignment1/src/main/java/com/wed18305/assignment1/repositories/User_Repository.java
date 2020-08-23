package com.wed18305.assignment1.repositories;

import java.util.Optional;

import com.wed18305.assignment1.model.User_model;
import org.springframework.data.repository.CrudRepository;

public interface User_Repository extends CrudRepository<User_model, Long> {

    @Override
    Iterable<User_model> findAllById(Iterable<Long> iterable);

    Optional<User_model> findByUsername(String username);

    Optional<User_model> findByUsernameAndPassword(String username, String password);

    Optional<User_model> findById(Long id);

    Iterable<User_model> findAllByUserTypeId(Long id);
}
