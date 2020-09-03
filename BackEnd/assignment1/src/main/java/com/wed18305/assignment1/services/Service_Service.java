package com.wed18305.assignment1.services;

import java.util.Optional;

import com.wed18305.assignment1.model.Entity_Service;
import com.wed18305.assignment1.repositories.Service_Repository;

import org.springframework.beans.factory.annotation.Autowired;

/*
    Services interact with a repository.
    They can update it or retreive info from it.
*/

@org.springframework.stereotype.Service // Can't import two classes with the same name.
public class Service_Service {
    @Autowired
    private Service_Repository serviceRepository;

    // Update or Retrieve Info From the Repository
    public Entity_Service saveOrUpdateService(Entity_Service service) {
        return serviceRepository.save(service);
    }

    public Optional<Entity_Service> findById(Long id){
        return serviceRepository.findById(id);
    }
}