package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.service.impl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserHeaderController {
    @Autowired
    private MovieServiceImpl movieService;


    @GetMapping("/search-in-header")
    public String searchInHeader(@RequestParam(name = "searchHeaderInput")String searchHeaderInput
    , Model model){
        List<Movie> movies = movieService.findMovieCustom(searchHeaderInput);
        System.out.println(movies.size());
        model.addAttribute("movies", movies);
        model.addAttribute("searchHeaderInput", searchHeaderInput);
        return "MovieList";
    }
}
