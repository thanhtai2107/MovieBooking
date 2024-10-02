package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.Schedule;
import com.example.MovieBooking.repository.ScheduleRepository;
import com.example.MovieBooking.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements IScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
    public Schedule getScheduleById(Long scheduleId) {
        return scheduleRepository.getOne(scheduleId);
    }
}
