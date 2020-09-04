package com.wed18305.assignment1.services;

import java.util.ArrayList;
import java.util.Optional;

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

    public ArrayList<Entity_Schedule> findByIds(Long[] ids){
        ArrayList<Entity_Schedule> schedules = new ArrayList<Entity_Schedule>();
        for (int i = 0; i < ids.length; i++) {
            Optional<Entity_Schedule> s = schRepository.findById(ids[i]);
            if(s.isPresent()){
                schedules.add(s.get());
            }
        }
        return schedules;
    }

    //DELETION
    public void deleteById(Long id){
        schRepository.deleteById(id);
    }
    public void deleteAll(Iterable<Entity_Schedule> schedules){
        schRepository.deleteAll(schedules);
    }
}