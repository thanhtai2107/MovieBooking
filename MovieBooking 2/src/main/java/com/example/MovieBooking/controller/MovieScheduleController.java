package com.example.MovieBooking.controller;

import org.springframework.ui.Model;
import com.example.MovieBooking.entity.MovieSchedule;
import com.example.MovieBooking.service.IMovieScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movieSchedules")
public class MovieScheduleController {

    @Autowired
    private IMovieScheduleService movieScheduleService;

    @GetMapping("/list")
    public String getAllMovieSchedules(Model model) {
        model.addAttribute("movieSchedules", movieScheduleService.getAllMovieSchedules());
        return "MovieSchedule/MovieScheduleList";
    }

    @GetMapping("/details/{id}")
    public String getMovieScheduleById(@PathVariable Long id, Model model) {
        MovieSchedule movieSchedule = movieScheduleService.getMovieScheduleById(id);
        if (movieSchedule != null) {
            model.addAttribute("movieSchedule", movieSchedule);
            return "MovieSchedule/MovieScheduleDetails";
        } else {
            return "redirect:/movieSchedules/list";
        }
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("movieSchedule", new MovieSchedule());
        return "MovieSchedule/CreateMovieSchedule";
    }

    @PostMapping("/create")
    public String createMovieSchedule(@ModelAttribute MovieSchedule movieSchedule) {
        movieScheduleService.saveMovieSchedule(movieSchedule);
        return "redirect:/movieSchedules/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MovieSchedule movieSchedule = movieScheduleService.getMovieScheduleById(id);
        if (movieSchedule != null) {
            model.addAttribute("movieSchedule", movieSchedule);
            return "MovieSchedule/EditMovieSchedule";
        } else {
            return "redirect:/movieSchedules/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateMovieSchedule(@PathVariable Long id, @ModelAttribute MovieSchedule movieSchedule) {
        movieScheduleService.saveMovieSchedule(movieSchedule);
        return "redirect:/movieSchedules/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteMovieSchedule(@PathVariable Long id) {
        movieScheduleService.deleteMovieSchedule(id);
        return "redirect:/movieSchedules/list";
    }
}