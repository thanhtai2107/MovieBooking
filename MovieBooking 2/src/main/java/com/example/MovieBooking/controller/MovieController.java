package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.entity.MovieSchedule;
import com.example.MovieBooking.entity.Schedule;
import com.example.MovieBooking.service.impl.MovieServiceImpl;
import com.example.MovieBooking.service.impl.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MovieController {
    @Autowired
    MovieServiceImpl movieService;

    @Autowired
    ScheduleServiceImpl scheduleService;

//    @GetMapping("/scheduled-movie")
//    public String viewSchedule(@RequestParam("movieId")Long movieId, Model model){
//        Movie movie = movieService.getMovieById(movieId);
//
//        List<Schedule> scheduleList = scheduleService.getAllSchedulesByMovieId(movieId);
//        List<MovieSchedule> movieScheduleList = new ArrayList<>();
//        for(Schedule schedule : scheduleList){
//            MovieSchedule movieSchedule = new MovieSchedule();
//            movieSchedule.setSchedule(schedule);
//            movieScheduleList.add(movieSchedule);
//        }
//        model.addAttribute("movie", movie);
//        model.addAttribute("scheduleList", scheduleList);
//        model.addAttribute("movieScheduleList", movieScheduleList);
//        return "a";
//    }
}
