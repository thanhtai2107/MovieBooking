package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.CinemaRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface CinemaRoomRepository extends JpaRepository<CinemaRoom, Long> {
    @Query("from CinemaRoom c where c.cinemaName like %?1%")
    Page<CinemaRoom> findByCinemaRoomName(String valueSearch, Pageable pageable);

    @Query("from CinemaRoom c where c.cinemaRoomId = ?1 or c.seatQuantity = ?1")
    Page<CinemaRoom> findByCinemaRoomIdOrSeatQuantity(Long valueSearch, Pageable pageable);
}