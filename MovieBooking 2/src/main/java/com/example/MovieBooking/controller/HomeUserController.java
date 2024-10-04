package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.Account;
import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.entity.Promotion;
import com.example.MovieBooking.service.IAccountService;
import com.example.MovieBooking.service.IMemberService;
import com.example.MovieBooking.service.impl.MovieServiceImpl;
import com.example.MovieBooking.service.impl.PromotionServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeUserController {

    @Autowired
    private MovieServiceImpl movieService;

    @Autowired
    private PromotionServiceImpl promotionService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IMemberService memberService;

    @GetMapping("/home")
    public String listAllHomeUser(Model model, HttpSession session, @AuthenticationPrincipal Account account) {
        List<Movie> listMovie = movieService.getAllMovies();
        List<Promotion> listPromotion = promotionService.getAllPromotions();
        if (account != null) {
            Account account1 = accountService.findUserByUsername(account.getUsername());
            Integer score = memberService.getTotalScore(account1.getAccountId());
            session.setAttribute("account", account1);
            session.setAttribute("score", score);
        }

        model.addAttribute("listMovie", listMovie);
        model.addAttribute("listPromotion", listPromotion);
        session.setAttribute("movieList", listMovie);
        return "homepageUser";
    }

//    public String listTypeMovie(HttpSession session) {
//        List<Movie> listMovie = movieService.getAllMovies();
//        session.setAttribute("listMovie", listMovie);
//        return "component/UserHeader";
//    }
}
