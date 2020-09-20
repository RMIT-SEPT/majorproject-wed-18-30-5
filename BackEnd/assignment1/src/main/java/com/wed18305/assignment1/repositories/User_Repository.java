package com.wed18305.assignment1.repositories;

import java.util.Optional;

import com.wed18305.assignment1.model.Entity_User;
import org.springframework.data.repository.CrudRepository;

public interface User_Repository extends CrudRepository<Entity_User, Long> {

    @Override
    Iterable<Entity_User> findAllById(Iterable<Long> iterable);

    Optional<Entity_User> findByUsername(String username);

    Optional<Entity_User> findByUsernameAndPassword(String username, String password);

    Optional<Entity_User> findById(Long id);

    Iterable<Entity_User> findAllByUserTypeId(Long id);
}
