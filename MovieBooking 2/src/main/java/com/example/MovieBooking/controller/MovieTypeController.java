package com.example.MovieBooking.controller;

import org.springframework.ui.Model;
import com.example.MovieBooking.entity.MovieType;
import com.example.MovieBooking.service.IMovieTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Controller class for handling movie type-related operations.
 *
 * @author Duong Le Phu An
 */
@Controller
@RequestMapping("/movieTypes")
public class MovieTypeController {

    @Autowired
    private IMovieTypeService movieTypeService;

    @GetMapping("/list")
    public String getAllMovieTypes(Model model) {
        model.addAttribute("movieTypes", movieTypeService.getAllMovieTypes());
        return "MovieType/MovieTypeList";
    }

    @GetMapping("/details/{id}")
    public String getMovieTypeById(@PathVariable Long id, Model model) {
        MovieType movieType = movieTypeService.getMovieTypeById(id);
        if (movieType != null) {
            model.addAttribute("movieType", movieType);
            return "MovieType/MovieTypeDetails";
        } else {
            return "redirect:/movieTypes/list";
        }
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("movieType", new MovieType());
        return "MovieType/CreateMovieType";
    }

    @PostMapping("/create")
    public String createMovieType(@ModelAttribute MovieType movieType) {
        movieTypeService.saveMovieType(movieType);
        return "redirect:/movieTypes/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MovieType movieType = movieTypeService.getMovieTypeById(id);
        if (movieType != null) {
            model.addAttribute("movieType", movieType);
            return "MovieType/EditMovieType";
        } else {
            return "redirect:/movieTypes/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateMovieType(@PathVariable Long id, @ModelAttribute MovieType movieType) {
        movieTypeService.saveMovieType(movieType);
        return "redirect:/movieTypes/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteMovieType(@PathVariable Long id) {
        movieTypeService.deleteMovieType(id);
        return "redirect:/movieTypes/list";
    }
}

