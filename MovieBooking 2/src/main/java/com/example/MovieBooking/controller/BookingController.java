package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.Booking;
import com.example.MovieBooking.service.impl.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    BookingServiceImpl bookingService;

    //find booked ticket - need to update find follow user ID
    @GetMapping("/booked-ticket")
    public String bookedTicket(@RequestParam(value = "page", defaultValue = "0")int page
                               ,@RequestParam(value = "size",defaultValue = "10")int size
                               ,@RequestParam(value = "searchInput", defaultValue = "", required = false)String searchInput
                                ,Model model){
//        List<Integer> entry = new ArrayList<>(10);
        String search = "";
        if(searchInput != null){
            search = searchInput;
        }
        Page<Booking> listBooking = bookingService.getBookingsPagination(search, page, size);
        System.out.println(listBooking.toList().size());
        model.addAttribute("listBooking", listBooking);
        model.addAttribute("totalPages", listBooking.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("search", search);
        return "BookedTicketManagement";
    }
}
