package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.CinemaRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRoomRepository extends JpaRepository<CinemaRoom, Long> {
}