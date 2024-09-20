package com.example.MovieBooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingController {

    @GetMapping("/booked-ticket")
    public String bookedTicket(){
        return "BookedTicketManagement";
    }
}
