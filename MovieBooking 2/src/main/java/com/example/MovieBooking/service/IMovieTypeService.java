package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.MovieType;
import java.util.List;

/**
 * Interface for movie type service operations.
 *
 * @author Duong Le Phu An
 */
public interface IMovieTypeService {
    MovieType saveMovieType(MovieType movieType);
    MovieType getMovieTypeById(Long id);
    List<MovieType> getAllMovieTypes();
    void deleteMovieType(Long id);
    void deleteByMovieId(Long movieId);
}
