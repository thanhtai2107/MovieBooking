package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.MovieSchedule;
import com.example.MovieBooking.repository.MovieScheduleRepository;
import com.example.MovieBooking.service.IMovieScheduleService;
import com.example.MovieBooking.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieScheduleServiceImpl implements IMovieScheduleService {

    private final MovieScheduleRepository movieScheduleRepository;

    @Autowired
    public MovieScheduleServiceImpl(MovieScheduleRepository movieScheduleRepository) {
        this.movieScheduleRepository = movieScheduleRepository;
    }

    @Override
    public List<MovieSchedule> getAllMovieSchedules() {
        return movieScheduleRepository.findAll();
    }

    @Override
    public MovieSchedule getMovieSchedule(Long movieId, Long scheduleId) {
        return movieScheduleRepository.findByMovie_MovieIdAndSchedule_ScheduleId(movieId, scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("MovieSchedule not found with movieId: " + movieId + " and scheduleId: " + scheduleId));
    }

    @Override
    public MovieSchedule saveMovieSchedule(MovieSchedule movieSchedule) {
        return movieScheduleRepository.save(movieSchedule);
    }

    @Override
    public void deleteMovieSchedule(Long movieId, Long scheduleId) {
        movieScheduleRepository.deleteByMovie_MovieIdAndSchedule_ScheduleId(movieId, scheduleId);
    }
}
