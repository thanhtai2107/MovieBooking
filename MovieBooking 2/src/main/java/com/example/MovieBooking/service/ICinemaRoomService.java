package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.CinemaRoom;
import com.example.MovieBooking.entity.Movie;

import java.util.List;

public interface ICinemaRoomService {
    List<CinemaRoom> getAllCinemaRooms();
    CinemaRoom getCinemaRoomById(Long id);
    CinemaRoom saveCinemaRoom(CinemaRoom cinemaRoom);
    void deleteCinemaRoom(Long id);
}
