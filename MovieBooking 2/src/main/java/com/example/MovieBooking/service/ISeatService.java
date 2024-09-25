package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.Seat;
import com.example.MovieBooking.entity.SeatType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISeatService {
    List<Seat> listSeatByCinemaRoomId(Long cinemaRoomId);

    Seat getSeatById(Long seatId);


    void updateListSeatType(Long[] listSeatIds, String valueSeatType);

    // Tìm tất cả ghế dựa trên scheduleId
   

}
