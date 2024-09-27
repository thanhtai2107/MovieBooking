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
    List<Schedule> findScheduleTimesAndMoviesByDateMovieId(@Param("date") LocalDate date, @Param("id")Long id);

    @Query(value = "select s.* from schedule s \n" +
            "join movie_schedule ms on s.schedule_id = ms.schedule_id\n" +
            "join movie m on ms.movie_id = m.movie_id\n" +
            "join movie_date md on md.movie_id = m.movie_id\n" +
            "join show_date sd on sd.show_date_id = md.show_date_id\n" +
            "where m.movie_id = :id and sd.show_date = :date", nativeQuery = true)
    List<Schedule>findScheduleTimesByDateAndMovieId(@Param("date") LocalDate date, @Param("id")Long id);

    @Query(" FROM Schedule s " +
            "JOIN s.movieScheduleList ms " +
            "JOIN ms.movie m " +
            "JOIN MovieDate md ON md.movie.movieId = m.movieId " +
            "JOIN md.showDate sd " +
            "WHERE m.movieId = :id")
    List<Schedule> findSchedulesMovie(Long id);


}
