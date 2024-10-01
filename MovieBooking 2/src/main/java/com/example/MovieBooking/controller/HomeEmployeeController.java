package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.Booking;
import com.example.MovieBooking.entity.Member;
import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.entity.Promotion;
import com.example.MovieBooking.service.IMemberService;
import com.example.MovieBooking.service.impl.BookingServiceImpl;
import com.example.MovieBooking.service.impl.MovieServiceImpl;
import com.example.MovieBooking.service.impl.PromotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeEmployeeController {

    @Autowired
    private IMemberService memberService;

    @Autowired
    private BookingServiceImpl bookingService;

    @GetMapping("/homeEmployee")
    public String homeEmployee(Model model) {
        List<Member> members = memberService.getAllMembers();
        List<Booking> listBooking = bookingService.findAll();
        int countBooking = listBooking.size();
        int countMember = members.size();
        model.addAttribute("listMember", members);
        model.addAttribute("countBooking", countBooking);
        model.addAttribute("countMember", countMember);
        return "homepageEmployee";
    }
}
