package com.wed18305.assignment1.repositories;

import com.wed18305.assignment1.model.User;
import org.springframework.data.repository.CrudRepository;

public interface User_Repository extends CrudRepository<User, Long>{

    @Override
    Iterable<User> findAllById(Iterable<Long> iterable);

}

