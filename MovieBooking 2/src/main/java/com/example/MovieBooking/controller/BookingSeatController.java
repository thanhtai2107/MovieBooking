package com.example.MovieBooking.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingSeatController{
    
    @GetMapping("/select-seat")
    public String showSelectSeat(
            @RequestParam("movieId")Long movieId,
            @RequestParam("scheduleId")Long scheduleId,
            Model model){
        // Xử lý logic laays dữ liệu từ moviedId và scheduleId
        model.addAttribute("movieId", movieId);
        model.addAttribute("scheduleId", scheduleId);
        return "TKS-selecttingseat";
    }
    
}
