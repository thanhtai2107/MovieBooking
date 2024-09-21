package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.MovieType;
import com.example.MovieBooking.service.IMovieTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie-types")
public class MovieTypeController {

    private final IMovieTypeService movieTypeService;

    @Autowired
    public MovieTypeController(IMovieTypeService movieTypeService) {
        this.movieTypeService = movieTypeService;
    }

    @GetMapping
    public List<MovieType> getAllMovieTypes() {
        return movieTypeService.getAllMovieTypes();
    }

    @GetMapping("/{movieId}/{typeId}")
    public MovieType getMovieType(@PathVariable Long movieId, @PathVariable Long typeId) {
        return movieTypeService.getMovieType(movieId, typeId);
    }

    @PostMapping
    public MovieType createMovieType(@RequestBody MovieType movieType) {
        return movieTypeService.saveMovieType(movieType);
    }

    @DeleteMapping("/{movieId}/{typeId}")
    public void deleteMovieType(@PathVariable Long movieId, @PathVariable Long typeId) {
        movieTypeService.deleteMovieType(movieId, typeId);
    }
}
