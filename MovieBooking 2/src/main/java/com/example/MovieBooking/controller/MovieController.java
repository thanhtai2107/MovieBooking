package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.*;
import com.example.MovieBooking.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final IMovieService movieService;
    private final ICinemaRoomService cinemaRoomService;
    private final IScheduleService scheduleService;
    private final ITypeService typeService;
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    public MovieController(IMovieService movieService, 
                           ICinemaRoomService cinemaRoomService, 
                           IScheduleService scheduleService,
                           ITypeService typeService) {
        this.movieService = movieService;
        this.cinemaRoomService = cinemaRoomService;
        this.scheduleService = scheduleService;
        this.typeService = typeService;
    }

    @GetMapping
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "Movie/MovieManager";
    }

    @GetMapping("/modal/add")
    public String getAddMovieModal(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("cinemaRooms", cinemaRoomService.getAllCinemaRooms());
        model.addAttribute("schedules", scheduleService.getAllSchedules());
        return "Movie/AddMovieModal";
    }

    @GetMapping("/modal/edit/{id}")
    public String getEditConfirmationModal(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieWithTypesAndSchedules(id);
        if (movie == null) {
            return "redirect:/movies";
        }
        model.addAttribute("movie", movie);
        model.addAttribute("allTypes", typeService.getAllTypes());
        model.addAttribute("cinemaRooms", cinemaRoomService.getAllCinemaRooms());
        model.addAttribute("schedules", scheduleService.getAllSchedules());
        return "Movie/EditMovieModal";
    }

    @GetMapping("/modal/delete/{id}")
    public String getDeleteConfirmationModal(@PathVariable Long id, Model model) {
        model.addAttribute("movieId", id);
        return "Movie/DeleteConfirmationModal";
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> createMovie(@Valid @ModelAttribute Movie movie,
                              BindingResult bindingResult,
                              @RequestParam(required = false) List<Long> movieTypes,
                              @RequestParam(required = false) List<Long> schedules) {
        logger.info("Received request to create movie: {}", movie.getNameEnglish());
        if (bindingResult.hasErrors() || !isValidMovie(movie, movieTypes, schedules)) {
            logger.warn("Invalid movie data received");
            return ResponseEntity.badRequest().body(getErrorMessages(bindingResult, movie, movieTypes, schedules));
        }
        try {
            Movie createdMovie = movieService.saveMovie(movie, movieTypes, schedules);
            logger.info("Movie created successfully: {}", createdMovie.getMovieId());
            return ResponseEntity.ok(createdMovie);
        } catch (Exception e) {
            logger.error("Error saving movie: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @Valid @ModelAttribute Movie movie,
                          BindingResult bindingResult,
                          @RequestParam(required = false) List<Long> movieTypes,
                          @RequestParam(required = false) List<Long> schedules) {
        if (bindingResult.hasErrors() || !isValidMovie(movie, movieTypes, schedules)) {
            return ResponseEntity.badRequest().body(getErrorMessages(bindingResult, movie, movieTypes, schedules));
        }
        
        try {
            Movie updatedMovie = movieService.updateMovie(id, movie, movieTypes, schedules);
            return ResponseEntity.ok(updatedMovie);
        } catch (Exception e) {
            logger.error("Error updating movie: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            movieService.deleteMovie(id);
            redirectAttributes.addFlashAttribute("successMessage", "Movie deleted successfully");
        } catch (Exception e) {
            logger.error("Error deleting movie: ", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting movie: " + e.getMessage());
        }
        return "redirect:/movies";
    }

    @GetMapping("/search")
    public String searchMovies(@RequestParam(required = false) String query, Model model) {
        List<Movie> movies;
        if (query != null && !query.trim().isEmpty()) {
            movies = movieService.searchMovies(query);
        } else {
            movies = movieService.getAllMovies();
        }
        model.addAttribute("movies", movies);
        model.addAttribute("searchQuery", query);
        return "Movie/MovieManager";
    }

    private boolean isValidMovie(Movie movie, List<Long> movieTypes, List<Long> schedules) {
        return movie.getFromDate() != null && movie.getToDate() != null &&
               !movie.getFromDate().isAfter(movie.getToDate()) &&
               movieTypes != null && !movieTypes.isEmpty() &&
               schedules != null && !schedules.isEmpty();
    }

    private String getErrorMessages(BindingResult bindingResult, Movie movie, List<Long> movieTypes, List<Long> schedules) {
        List<String> errors = new ArrayList<>();
        
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        }
        
        if (movie.getFromDate() == null || movie.getToDate() == null) {
            errors.add("From date and To date are required");
        } else if (movie.getFromDate().isAfter(movie.getToDate())) {
            errors.add("From date cannot be later than To date");
        }
        
        if (movieTypes == null || movieTypes.isEmpty()) {
            errors.add("At least one movie type must be selected");
        }
        
        if (schedules == null || schedules.isEmpty()) {
            errors.add("At least one schedule must be selected");
        }
        
        return "Error creating movie: " + String.join(", ", errors);
    }
}

