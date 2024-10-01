package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.Schedule;
import com.example.MovieBooking.repository.ScheduleRepository;
import com.example.MovieBooking.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImpl implements IScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    public List<Schedule> getAllSchedulesByDateAndId(LocalDate localDate, Long id) {
        return scheduleRepository.findScheduleTimesAndMoviesByDateMovieId(localDate,id);
    }

    public List<Schedule> getAllSchedulesByMovieId(LocalDate localDate, Long movieId) {
        return scheduleRepository.findSchedulesMovie(movieId);
    }

    public List<Schedule> getAllSchedulesByDateAndMovieIdCustom(LocalDate localDate, Long movieId) {
        return scheduleRepository.findScheduleTimesByDateAndMovieId(localDate,movieId);
    }
    
}
