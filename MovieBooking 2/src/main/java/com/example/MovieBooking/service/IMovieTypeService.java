package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.MovieType;

import java.util.List;

public interface IMovieTypeService {
    List<MovieType> getAllMovieTypes();
    MovieType getMovieType(Long movieId, Long typeId);
    MovieType saveMovieType(MovieType movieType);
    void deleteMovieType(Long movieId, Long typeId);
}
