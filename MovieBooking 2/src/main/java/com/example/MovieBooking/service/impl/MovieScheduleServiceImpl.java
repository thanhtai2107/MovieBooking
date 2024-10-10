package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.MovieSchedule;
import com.example.MovieBooking.repository.MovieScheduleRepository;
import com.example.MovieBooking.service.IMovieScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing movie schedules.
 *
 * @author Duong Le Phu An
 */
@Service
public class MovieScheduleServiceImpl implements IMovieScheduleService {

    @Autowired
    private MovieScheduleRepository movieScheduleRepository;

    @Override
    public MovieSchedule saveMovieSchedule(MovieSchedule movieSchedule) {
        return movieScheduleRepository.save(movieSchedule);
    }

    @Override
    public MovieSchedule getMovieScheduleById(Long id) {
        return movieScheduleRepository.findById(id).orElse(null);
    }

    @Override
    public List<MovieSchedule> getAllMovieSchedules() {
        return movieScheduleRepository.findAll();
    }

    @Override
    public void deleteMovieSchedule(Long id) {
        movieScheduleRepository.deleteById(id);
    }

    @Override
    public void deleteByMovieId(Long movieId) {
        movieScheduleRepository.deleteByMovieId(movieId);
    }
}