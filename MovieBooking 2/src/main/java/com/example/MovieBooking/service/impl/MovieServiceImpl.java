package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.repository.MovieRepository;
import com.example.MovieBooking.service.IMovieService;
import com.example.MovieBooking.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements IMovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    }

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public List<Movie> searchMovies(String query) {
        // Implement search logic here. This is a simple example and might need to be adjusted based on your requirements.
        return movieRepository.findByNameEnglishContainingOrNameVNContaining(query, query);
    }
}