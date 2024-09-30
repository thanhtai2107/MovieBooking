package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.MovieDate;

import java.util.List;

public interface IMovieDateService {
    List<MovieDate> getAllMovieDates();
    MovieDate getMovieDateById(Long id);
    MovieDate saveMovieDate(MovieDate movieDate);
    void deleteMovieDate(Long id);
}
