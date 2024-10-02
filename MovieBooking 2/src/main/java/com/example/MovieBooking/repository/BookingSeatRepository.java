package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.BookingSeat;
import com.example.MovieBooking.entity.ShowDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {
    // Tìm danh sách các ghế đã đặt dựa trên movieId và scheduleId
    @Query("SELECT bs FROM BookingSeat bs " +
            "JOIN bs.movie m " +
            "JOIN bs.schedule s " +
            "JOIN bs.showDate sd " +
            "WHERE sd.showDate = :showDate " +
            "AND m.movieId = :movieId " +
            "AND s.scheduleId = :scheduleId")
    List<BookingSeat> findBookedSeatsByMovieAndScheduleAndShowDate(@Param("movieId") Long movieId,
                                                        @Param("scheduleId") Long scheduleId,
                                                        @Param("showDate") LocalDate showDate);
}
