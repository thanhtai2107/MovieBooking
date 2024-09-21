package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.MovieSchedule;
import com.example.MovieBooking.service.IMovieScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie-schedules")
public class MovieScheduleController {

    private final IMovieScheduleService movieScheduleService;

    @Autowired
    public MovieScheduleController(IMovieScheduleService movieScheduleService) {
        this.movieScheduleService = movieScheduleService;
    }

    @GetMapping
    public List<MovieSchedule> getAllMovieSchedules() {
        return movieScheduleService.getAllMovieSchedules();
    }

    @GetMapping("/{movieId}/{scheduleId}")
    public MovieSchedule getMovieSchedule(@PathVariable Long movieId, @PathVariable Long scheduleId) {
        return movieScheduleService.getMovieSchedule(movieId, scheduleId);
    }

    @PostMapping
    public MovieSchedule createMovieSchedule(@RequestBody MovieSchedule movieSchedule) {
        return movieScheduleService.saveMovieSchedule(movieSchedule);
    }

    @DeleteMapping("/{movieId}/{scheduleId}")
    public void deleteMovieSchedule(@PathVariable Long movieId, @PathVariable Long scheduleId) {
        movieScheduleService.deleteMovieSchedule(movieId, scheduleId);
    }
}
