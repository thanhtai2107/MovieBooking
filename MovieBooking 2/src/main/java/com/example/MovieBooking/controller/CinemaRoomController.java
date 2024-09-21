package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.CinemaRoom;
import com.example.MovieBooking.service.ICinemaRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cinema-rooms")
public class CinemaRoomController {

    private final ICinemaRoomService cinemaRoomService;

    @Autowired
    public CinemaRoomController(ICinemaRoomService cinemaRoomService) {
        this.cinemaRoomService = cinemaRoomService;
    }

    @GetMapping
    public List<CinemaRoom> getAllCinemaRooms() {
        return cinemaRoomService.getAllCinemaRooms();
    }

    @GetMapping("/{id}")
    public CinemaRoom getCinemaRoomById(@PathVariable Long id) {
        return cinemaRoomService.getCinemaRoomById(id);
    }

    @PostMapping
    public CinemaRoom createCinemaRoom(@RequestBody CinemaRoom cinemaRoom) {
        return cinemaRoomService.saveCinemaRoom(cinemaRoom);
    }

    @PutMapping("/{id}")
    public CinemaRoom updateCinemaRoom(@PathVariable Long id, @RequestBody CinemaRoom cinemaRoom) {
        cinemaRoom.setCinemaRoomId(id);
        return cinemaRoomService.saveCinemaRoom(cinemaRoom);
    }

    @DeleteMapping("/{id}")
    public void deleteCinemaRoom(@PathVariable Long id) {
        cinemaRoomService.deleteCinemaRoom(id);
    }
}
