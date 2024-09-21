package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.MovieSchedule;
import com.example.MovieBooking.repository.MovieScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IMovieScheduleService {
    List<MovieSchedule> getAllMovieSchedules();
    MovieSchedule getMovieSchedule(Long movieId, Long scheduleId);
    MovieSchedule saveMovieSchedule(MovieSchedule movieSchedule);
    void deleteMovieSchedule(Long movieId, Long scheduleId);
}