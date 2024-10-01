package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.Schedule;
import java.util.List;
public interface IScheduleService {
    Schedule saveSchedule(Schedule schedule);
    Schedule getScheduleById(Long id);
    List<Schedule> getAllSchedules();
    void deleteSchedule(Long id);
}

