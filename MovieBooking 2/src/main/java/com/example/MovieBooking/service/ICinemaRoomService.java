package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.CinemaRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ICinemaRoomService {
    List<CinemaRoom> getAllCinemaRooms();
    Page<CinemaRoom> getCinemaRoomsByName(String valueSearch, int page, int size);

    Page<CinemaRoom> getCinemaRoomsByIdOrBySeatQuantity(Long valueSearch, int page, int size);

    CinemaRoom getCinemaRoomById(Long cinemaRoomId);

    boolean existsByCinemaRoomName(String cinemaRoomName);

    void saveCinemaRoom(CinemaRoom cinemaRoom);
}

