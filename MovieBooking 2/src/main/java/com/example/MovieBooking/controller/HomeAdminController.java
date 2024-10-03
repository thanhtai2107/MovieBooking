package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.Booking;
import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.entity.Promotion;
import com.example.MovieBooking.service.impl.BookingServiceImpl;
import com.example.MovieBooking.service.impl.MovieServiceImpl;
import com.example.MovieBooking.service.impl.PromotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeAdminController {

    @Autowired
    private MovieServiceImpl movieService;

    @Autowired
    private PromotionServiceImpl promotionService;

    @Autowired
    private BookingServiceImpl bookingService;

    @GetMapping("/homeAdmin")
    public String homeAdmin(Model model) {
        List<Movie> listMovie = movieService.getAllMovies();
        List<Promotion> listPromotion = promotionService.getAllPromotions();
        List<Booking> listBooking = bookingService.findAll();
        int countMovie = listMovie.size();
        int countPromotion = listPromotion.size();
        int countBooking = listBooking.size();
        model.addAttribute("listMovie", listMovie);
        model.addAttribute("countMovie", countMovie);
        model.addAttribute("countBooking", countBooking);
        model.addAttribute("countPromotion", countPromotion);
        return "homepageAdmin";
    }
}
