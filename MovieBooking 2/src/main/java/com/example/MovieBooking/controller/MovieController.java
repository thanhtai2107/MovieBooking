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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 * Controller class for handling movie-related operations.
 *
 * @author Duong Le Phu An
 */
@Controller
@RequestMapping("/movies")
public class MovieController {

    private final IMovieService movieService;
    private final ICinemaRoomService cinemaRoomService;
    private final IScheduleService scheduleService;
    private final ITypeService typeService;
    private final IUploadImage uploadImageService;
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    /**
     * Constructor for MovieController.
     *
     * @author Duong Le Phu An
     */
    @Autowired
    public MovieController(IMovieService movieService, 
                           ICinemaRoomService cinemaRoomService, 
                           IScheduleService scheduleService,
                           ITypeService typeService,
                           IUploadImage uploadImageService) {
        this.movieService = movieService;
        this.cinemaRoomService = cinemaRoomService;
        this.scheduleService = scheduleService;
        this.typeService = typeService;
        this.uploadImageService = uploadImageService;
    }

    /**
     * Retrieves all movies and displays them in the MovieManager view.
     *
     * @param model the Model object to add attributes
     * @return the name of the view to render
     * @author Duong Le Phu An
     */
    @GetMapping
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "Movie/MovieManager";
    }

    /**
     * Displays the modal for adding a new movie.
     *
     * @param model the Model object to add attributes
     * @return the name of the view to render
     * @author Duong Le Phu An
     */
    @GetMapping("/modal/add")
    public String getAddMovieModal(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("cinemaRooms", cinemaRoomService.getAllCinemaRooms());
        model.addAttribute("schedules", scheduleService.getAllSchedules());
        return "Movie/AddMovieModal";
    }

    /**
     * Displays the modal for editing an existing movie.
     *
     * @param id the ID of the movie to edit
     * @param model the Model object to add attributes
     * @return the name of the view to render
     * @author Duong Le Phu An
     */
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

    /**
     * Displays the modal for confirming movie deletion.
     *
     * @param id the ID of the movie to delete
     * @param model the Model object to add attributes
     * @return the name of the view to render
     * @author Duong Le Phu An
     */
    @GetMapping("/modal/delete/{id}")
    public String getDeleteConfirmationModal(@PathVariable Long id, Model model) {
        model.addAttribute("movieId", id);
        return "Movie/DeleteConfirmationModal";
    }

    /**
     * Creates a new movie.
     *
     * @param movie the Movie object to create
     * @param bindingResult the BindingResult object for validation errors
     * @param movieTypes the list of movie type IDs
     * @param schedules the list of schedule IDs
     * @param imageFile the image file for the movie
     * @return ResponseEntity with the created movie or error messages
     * @author Duong Le Phu An
     */
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> createMovie(@Valid @ModelAttribute Movie movie,
                              BindingResult bindingResult,
                              @RequestParam(required = false) List<Long> movieTypes,
                              @RequestParam(required = false) List<Long> schedules,
                              @RequestParam("imageFile") MultipartFile imageFile) {
        logger.info("Received request to create movie: {}", movie.getNameEnglish());
        logger.info("Image file received: name={}, size={}, contentType={}", 
                    imageFile.getOriginalFilename(), 
                    imageFile.getSize(), 
                    imageFile.getContentType());
        
        if (bindingResult.hasErrors() || !isValidMovie(movie, movieTypes, schedules)) {
            logger.warn("Invalid movie data received");
            return ResponseEntity.badRequest().body(getErrorMessages(bindingResult, movie, movieTypes, schedules));
        }
        try {
            if (!imageFile.isEmpty()) {
                String imageUrl = uploadImageService.uploadImage(imageFile);
                logger.info("Image uploaded successfully. URL: {}", imageUrl);
                movie.setLargeImage(imageUrl);
                movie.setSmallImage(imageUrl); // Set the same URL for smallImage
            } else {
                logger.warn("No image file received or file is empty");
            }
            Movie createdMovie = movieService.saveMovie(movie, movieTypes, schedules);
            logger.info("Movie created successfully: {}", createdMovie.getMovieId());
            return ResponseEntity.ok(createdMovie);
        } catch (Exception e) {
            logger.error("Error saving movie: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    /**
     * Updates an existing movie.
     *
     * @param id the ID of the movie to update
     * @param movie the updated Movie object
     * @param bindingResult the BindingResult object for validation errors
     * @param movieTypes the list of movie type IDs
     * @param schedules the list of schedule IDs
     * @param imageFile the new image file for the movie (optional)
     * @return ResponseEntity with the updated movie or error messages
     * @author Duong Le Phu An
     */
    @PostMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @Valid @ModelAttribute Movie movie,
                          BindingResult bindingResult,
                          @RequestParam(required = false) List<Long> movieTypes,
                          @RequestParam(required = false) List<Long> schedules,
                          @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        if (bindingResult.hasErrors() || !isValidMovie(movie, movieTypes, schedules)) {
            return ResponseEntity.badRequest().body(getErrorMessages(bindingResult, movie, movieTypes, schedules));
        }
        
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = uploadImageService.uploadImage(imageFile);
                movie.setLargeImage(imageUrl);
                movie.setSmallImage(imageUrl); // Set the same URL for smallImage
            }
            Movie updatedMovie = movieService.updateMovie(id, movie, movieTypes, schedules);
            return ResponseEntity.ok(updatedMovie);
        } catch (Exception e) {
            logger.error("Error updating movie: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    /**
     * Deletes a movie.
     *
     * @param id the ID of the movie to delete
     * @param redirectAttributes the RedirectAttributes object for flash messages
     * @return the redirect URL
     * @author Duong Le Phu An
     */
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

    /**
     * Searches for movies based on a query.
     *
     * @param query the search query
     * @param model the Model object to add attributes
     * @return the name of the view to render
     * @author Duong Le Phu An
     */
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

    /**
     * Validates a movie object.
     *
     * @param movie the Movie object to validate
     * @param movieTypes the list of movie type IDs
     * @param schedules the list of schedule IDs
     * @return true if the movie is valid, false otherwise
     * @author Duong Le Phu An
     */
    private boolean isValidMovie(Movie movie, List<Long> movieTypes, List<Long> schedules) {
        return movie.getFromDate() != null && movie.getToDate() != null &&
               !movie.getFromDate().isAfter(movie.getToDate()) &&
               movieTypes != null && !movieTypes.isEmpty() &&
               schedules != null && !schedules.isEmpty();
    }

    /**
     * Generates error messages for invalid movie data.
     *
     * @param bindingResult the BindingResult object containing validation errors
     * @param movie the Movie object being validated
     * @param movieTypes the list of movie type IDs
     * @param schedules the list of schedule IDs
     * @return a string containing all error messages
     * @author Duong Le Phu An
     */
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