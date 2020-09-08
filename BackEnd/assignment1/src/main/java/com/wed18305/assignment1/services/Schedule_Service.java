package com.wed18305.assignment1.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.wed18305.assignment1.model.Entity_Schedule;
import com.wed18305.assignment1.repositories.Schedule_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Schedule_Service {
    @Autowired
    private Schedule_Repository schRepository;

    public Entity_Schedule saveOrUpdateSchedule(Entity_Schedule sch) {
        return schRepository.save(sch);
    }

    public Optional<Entity_Schedule> findById(Long id){
        return schRepository.findById(id);
    }

    public Iterable<Entity_Schedule> findByIds(Long[] ids){
        //Create a set of Ids
        Set<Long> idsSet = new HashSet<Long>();
        for (Long id : ids) {
            try {
                idsSet.add(id);
            } catch (Exception e) {
                System.out.println(e.getClass().getCanonicalName());
                System.out.println(e.getMessage());
            }
        }
        return schRepository.findAllById(idsSet);
    }

    //DELETION
    public void deleteById(Long id){
        schRepository.deleteById(id);
    }
    public void deleteAll(Iterable<Entity_Schedule> schedules){
        schRepository.deleteAll(schedules);
    }
}