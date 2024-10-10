package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.MovieSchedule;
import java.util.List;

/**
 * Interface for movie schedule service operations.
 *
 * @author Duong Le Phu An
 */
public interface IMovieScheduleService {
    MovieSchedule saveMovieSchedule(MovieSchedule movieSchedule);
    MovieSchedule getMovieScheduleById(Long id);
    List<MovieSchedule> getAllMovieSchedules();
    void deleteMovieSchedule(Long id);
    void deleteByMovieId(Long movieId);
}