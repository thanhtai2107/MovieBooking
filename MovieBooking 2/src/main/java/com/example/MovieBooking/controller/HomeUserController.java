package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.service.impl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeUserController {

    @Autowired
    private MovieServiceImpl movieService;

    @GetMapping("/home")
    public String listAllHomeUser(Model model) {
        List<Movie> listMovie = movieService.getAllMovies();

        return null;
    }
}
