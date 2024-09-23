package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.Seat;
import com.example.MovieBooking.entity.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("SELECT s FROM Seat s WHERE s.cinemaRoom.cinemaRoomId = :cinemaRoomId")
    List<Seat> findByCinemaRoomId(Long cinemaRoomId);
}
