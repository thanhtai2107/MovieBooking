package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.Movie;

import java.time.LocalDate;
import java.util.List;

public interface IMovieService {
    public List<Movie> getMoviesByDate(LocalDate date);

}
