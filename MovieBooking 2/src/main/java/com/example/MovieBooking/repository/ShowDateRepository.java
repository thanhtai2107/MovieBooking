package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.ShowDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ShowDateRepository extends JpaRepository<ShowDate, Long> {
    ShowDate findByShowDate(LocalDate showDate);
}
