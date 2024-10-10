package com.example.MovieBooking.controller;

import org.springframework.ui.Model;
import com.example.MovieBooking.entity.MovieDate;
import com.example.MovieBooking.service.IMovieDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling movie date-related operations.
 *
 * @author Duong Le Phu An
 */

@Controller
@RequestMapping("/movieDates")
public class MovieDateController {

    @Autowired
    private IMovieDateService movieDateService;

    @GetMapping("/list")
    public String getAllMovieDates(Model model) {
        model.addAttribute("movieDates", movieDateService.getAllMovieDates());
        return "MovieDate/MovieDateList";
    }

    @GetMapping("/details/{id}")
    public String getMovieDateById(@PathVariable Long id, Model model) {
        MovieDate movieDate = movieDateService.getMovieDateById(id);
        if (movieDate != null) {
            model.addAttribute("movieDate", movieDate);
            return "MovieDate/MovieDateDetails";
        } else {
            return "redirect:/movieDates/list";
        }
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("movieDate", new MovieDate());
        return "MovieDate/CreateMovieDate";
    }

    @PostMapping("/create")
    public String createMovieDate(@ModelAttribute MovieDate movieDate) {
        movieDateService.saveMovieDate(movieDate);
        return "redirect:/movieDates/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MovieDate movieDate = movieDateService.getMovieDateById(id);
        if (movieDate != null) {
            model.addAttribute("movieDate", movieDate);
            return "MovieDate/EditMovieDate";
        } else {
            return "redirect:/movieDates/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateMovieDate(@PathVariable Long id, @ModelAttribute MovieDate movieDate) {
        movieDateService.saveMovieDate(movieDate);
        return "redirect:/movieDates/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteMovieDate(@PathVariable Long id) {
        movieDateService.deleteMovieDate(id);
        return "redirect:/movieDates/list";
    }
}
