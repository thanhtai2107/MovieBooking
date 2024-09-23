package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.Seat;
import com.example.MovieBooking.entity.SeatType;

import java.util.List;

public interface ISeatService {
    List<Seat> listSeatByCinemaRoomId(Long cinemaRoomId);

    Seat getSeatById(Long seatId);


    void updateListSeatType(Long[] listSeatIds, String valueSeatType);

}
