package com.example.MovieBooking.service;
import com.example.MovieBooking.entity.Movie;
import java.util.List;

import com.example.MovieBooking.entity.Movie;

import java.time.LocalDate;
import java.util.List;

public interface IMovieService {
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    Movie saveMovie(Movie movie, List<Long> typeIds, List<Long> scheduleIds);
    Movie updateMovie(Long id, Movie updatedMovie, List<Long> typeIds, List<Long> scheduleIds);
    void deleteMovie(Long id);
    List<Movie> searchMovies(String query);
    Movie getMovieByIdWithSchedules(Long id);
    Movie getMovieWithTypesAndSchedules(Long id);
    public List<Movie> getMoviesByDate(LocalDate date);
    List<Movie> searchMovie(String searchInput);
}
