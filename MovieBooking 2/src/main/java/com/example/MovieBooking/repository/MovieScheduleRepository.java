package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.MovieSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, Long> {
    Optional<MovieSchedule> findByMovie_MovieIdAndSchedule_ScheduleId(Long movieId, Long scheduleId);
    void deleteByMovie_MovieIdAndSchedule_ScheduleId(Long movieId, Long scheduleId);

    // You can add more custom query methods here if needed
    // For example:
    // List<MovieSchedule> findByMovie_MovieId(Long movieId);
    // List<MovieSchedule> findBySchedule_ScheduleId(Long scheduleId);
}
