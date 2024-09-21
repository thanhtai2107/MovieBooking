package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.entity.CinemaRoom;
import com.example.MovieBooking.entity.Type;
import com.example.MovieBooking.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface IMovieService {
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    Movie saveMovie(Movie movie);
    void deleteMovie(Long id);
    List<Movie> searchMovies(String query);
}
