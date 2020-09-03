package com.wed18305.assignment1.services;

import java.util.Optional;
import com.wed18305.assignment1.model.Entity_UserType;
import com.wed18305.assignment1.repositories.UserType_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserType_Service {
    @Autowired
    private UserType_Repository userTypeRepository;

    public Entity_UserType saveNewUserType(Entity_UserType userType) {
        //TODO check if this updates if so dont allow it.
        return userTypeRepository.save(userType);
    }

    public Optional<Entity_UserType> findById(Long id){
        return userTypeRepository.findById(id);
    }
}