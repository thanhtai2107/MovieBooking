package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.Account;
import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.entity.Promotion;
import com.example.MovieBooking.service.IAccountService;
import com.example.MovieBooking.service.IMemberService;
import com.example.MovieBooking.service.IMovieService;
import com.example.MovieBooking.service.IPromotionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/*
* @Account: Nguyen Cong Van(display listMovie and listPromotion), Le Thanh Tri(get Score of User and code UI of home user page)
* @BirthDate: 2001/04/01
* @Desc: Controller for the user homepage.
* This controller is responsible for handling requests to the homepage for regular users.
*
* It retrieves a list of movies and promotions to display, and if the user is authenticated,
* it fetches the user's account information and membership score to display on the homepage.
*
* The data is passed to the view for rendering the homepage.
 * */
@Controller
public class HomeUserController {

    @Autowired
    private IMovieService movieService;

    @Autowired
    private IPromotionService promotionService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IMemberService memberService;

    /**
     * Handles GET requests for the user homepage.
     *
     * This method retrieves a list of movies and promotions to display on the homepage.
     * If the user is authenticated, it fetches their account information and total score from the session.
     * The account and score information are added to the session attributes.
     * The movie list and promotion list are added to the model for rendering.
     *
     * @param model the Model object used to pass data to the view.
     * @param session the HttpSession object used to manage session attributes.
     * @param account the authenticated user's account details provided by Spring Security.
     * @return the name of the view template for the user homepage, "homepageUser".
     */
    @GetMapping("/home")
    public String listAllHomeUser(Model model, HttpSession session, @AuthenticationPrincipal Account account) {
        // Retrieve all movies and promotions.
        List<Movie> listMovie = movieService.getAllMovies();
        List<Promotion> listPromotion = promotionService.getAllPromotions();

        // If the user is authenticated, fetch their account information and score.
        if (account != null) {
            Account account1 = accountService.findUserByUsername(account.getUsername());
            Integer score = memberService.getTotalScore(account1.getAccountId());
            session.setAttribute("account", account1);
            session.setAttribute("score", score);
        }

        // Add the movie list and promotion list to the model.
        model.addAttribute("listMovie", listMovie);
        model.addAttribute("listPromotion", listPromotion);

        // Store the movie list in the session.
        session.setAttribute("movieList", listMovie);

        // Return the view name for the user homepage.
        return "homepageUser";
    }
}
