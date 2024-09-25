package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {
    // Tìm danh sách các ghế đã đặt dựa trên movieId và scheduleId
    @Query("SELECT bs FROM BookingSeat bs WHERE bs.movie.movieId = :movieId AND bs.schedule.scheduleId = :scheduleId")
    List<BookingSeat> findBookedSeatsByMovieAndSchedule(@Param("movieId") Long movieId, @Param("scheduleId") Long scheduleId);
}
