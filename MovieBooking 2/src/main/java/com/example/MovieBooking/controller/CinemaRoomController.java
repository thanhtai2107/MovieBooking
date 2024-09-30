package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.CinemaRoom;
import com.example.MovieBooking.service.ICinemaRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cinemaRooms")
public class CinemaRoomController {

    @Autowired
    private ICinemaRoomService cinemaRoomService;

    @GetMapping("/list")
    public List<CinemaRoom> getAllCinemaRooms() {
        return cinemaRoomService.getAllCinemaRooms();
    }
}