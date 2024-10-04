package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.service.IMovieService;
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
    private IMovieService movieService;

    /**
     * Search movie in the header
     * @author Le Thanh Tri
     * @param searchHeaderInput
     * @param model
     * @return "MovieList"
     */
    @GetMapping("/search-in-header")
    public String searchInHeader(@RequestParam(name = "searchHeaderInput")String searchHeaderInput
            , Model model){
        List<Movie> moviesList = movieService.searchMovie(searchHeaderInput);
        model.addAttribute("movies", moviesList);
        model.addAttribute("searchHeaderInput", searchHeaderInput);
        return "MovieList";
    }
}
