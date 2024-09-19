package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.CinemaRoom;
import com.example.MovieBooking.service.impl.CinemaRoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cinemaRoom")
public class CinemaRoomController {

    @Autowired
    private CinemaRoomServiceImpl cinemaRoomService;

    @GetMapping("/listCinemaRoom")
    public String listCinemaRoom(Model model) {
        List<CinemaRoom> listCinemaRoom = cinemaRoomService.getAllCinemaRooms();
        model.addAttribute("listCinemaRoom", listCinemaRoom);
        return "cinemaRoom/listCinemaRoom";
    }

    @GetMapping("/addCinemaRoom")
    public String formAddCinemaRoom(CinemaRoom cinemaRoom) {
        return "cinemaRoom/addCinemaRoom";
    }

    @PostMapping
    public String addCinemaRoom(CinemaRoom cinemaRoom) {

    }

}
