package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.MovieType;
import java.util.List;

public interface IMovieTypeService {
    MovieType saveMovieType(MovieType movieType);
    MovieType getMovieTypeById(Long id);
    List<MovieType> getAllMovieTypes();
    void deleteMovieType(Long id);
    void deleteByMovieId(Long movieId);
}
