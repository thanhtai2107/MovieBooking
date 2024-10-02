package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.MovieSchedule;
import com.example.MovieBooking.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(" FROM Schedule s " +
            "JOIN s.movieScheduleList ms " +
            "JOIN ms.movie m " +
            "JOIN MovieDate md ON md.movie.movieId = m.movieId " +
            "JOIN md.showDate sd " +
            "WHERE sd.showDate = :date AND m.movieId = :id")
    List<Schedule> findScheduleTimesAndMoviesByDate(@Param("date") LocalDate date, Long id);
    
    

}
