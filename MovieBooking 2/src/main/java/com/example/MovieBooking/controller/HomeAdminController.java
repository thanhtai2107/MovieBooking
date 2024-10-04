package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.Booking;
import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.entity.Promotion;
import com.example.MovieBooking.service.IBookingService;
import com.example.MovieBooking.service.IMovieService;
import com.example.MovieBooking.service.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/*
* @Account: Nguyen Cong Van
* @BirthDate: 2001/04/01
* @Desc: Controller for the admin homepage.
* This controller is responsible for handling requests to the admin homepage. It retrieves data such as the list of movies,
* promotions, and bookings, and calculates the counts of each entity to display on the admin dashboard.
*
* The data is added to the model and passed to the view for rendering the homepage.
* */
@Controller
public class HomeAdminController {

    @Autowired
    private IMovieService movieService;

    @Autowired
    private IPromotionService promotionService;

    @Autowired
    private IBookingService bookingService;

    /**
     * Handles GET requests for the admin homepage.
     *
     * This method retrieves the list of all movies, promotions, and bookings from their respective services.
     * It calculates the count of movies, promotions, and bookings and adds them to the model.
     * The data is then passed to the "homepageAdmin" view, which is the admin dashboard page.
     *
     * @param model the Model object used to pass data to the view.
     * @return the name of the view template for the admin homepage, "homepageAdmin".
     */
    @GetMapping("/homeAdmin")
    public String homeAdmin(Model model) {
        // Retrieve all movies, promotions, and bookings.
        List<Movie> listMovie = movieService.getAllMovies();
        List<Promotion> listPromotion = promotionService.getAllPromotions();
        List<Booking> listBooking = bookingService.findAll();

        // Calculate the counts of each entity.
        int countMovie = listMovie.size();
        int countPromotion = listPromotion.size();
        int countBooking = listBooking.size();

        // Add the data and counts to the model.
        model.addAttribute("listMovie", listMovie);
        model.addAttribute("countMovie", countMovie);
        model.addAttribute("countBooking", countBooking);
        model.addAttribute("countPromotion", countPromotion);

        // Return the view name for the admin homepage.
        return "homepageAdmin";
    }
}
