package com.wed18305.assignment1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wed18305.assignment1.model.Schedule;
import com.wed18305.assignment1.repositories.Schedule_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Schedule_Service {
    @Autowired
    private Schedule_Repository schRepository;

    public Schedule saveOrUpdateUser(Schedule sch) {
        return schRepository.save(sch);
    }

    public Optional<Schedule> findById(Long id){
        return schRepository.findById(id);
    }
}