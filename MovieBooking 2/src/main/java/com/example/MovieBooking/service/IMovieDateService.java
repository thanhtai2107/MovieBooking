package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.MovieDate;

import java.util.List;

/**
 * Interface for movie date service operations.
 *
 * @author Duong Le Phu An
 */
public interface IMovieDateService {
    List<MovieDate> getAllMovieDates();
    MovieDate getMovieDateById(Long id);
    MovieDate saveMovieDate(MovieDate movieDate);
    void deleteMovieDate(Long id);
}
