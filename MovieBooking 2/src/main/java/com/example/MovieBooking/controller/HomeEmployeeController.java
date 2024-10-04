package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.Booking;
import com.example.MovieBooking.entity.Member;
import com.example.MovieBooking.service.IBookingService;
import com.example.MovieBooking.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/*
* @Account: Nguyen Cong Van
* @BirthDate: 2001/04/01
* @Desc: Controller for the employee homepage.
* This controller is responsible for handling requests to the employee homepage.
*
* It retrieves data such as the list of members and bookings, and calculates the
* counts of each entity to display on the employee dashboard.
*
* The data is added to the model and passed to the view for rendering the homepage.
 * */
@Controller
public class HomeEmployeeController {

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IBookingService bookingService;


    /**
     * Handles GET requests for the employee homepage.
     *
     * This method retrieves the list of all members and bookings from their respective services.
     * It calculates the count of members and bookings and adds them to the model.
     * The data is then passed to the "homepageEmployee" view, which is the employee dashboard page.
     *
     * @param model the Model object used to pass data to the view.
     * @return the name of the view template for the employee homepage, "homepageEmployee".
     */
    @GetMapping("/homeEmployee")
    public String homeEmployee(Model model) {
        // Retrieve all members and bookings.
        List<Member> members = memberService.getAllMembers();
        List<Booking> listBooking = bookingService.findAll();

        // Calculate the counts of members and bookings.
        int countBooking = listBooking.size();
        int countMember = members.size();

        // Add the data and counts to the model.
        model.addAttribute("listMember", members);
        model.addAttribute("countBooking", countBooking);
        model.addAttribute("countMember", countMember);

        // Return the view name for the employee homepage.
        return "homepageEmployee";
    }
}
