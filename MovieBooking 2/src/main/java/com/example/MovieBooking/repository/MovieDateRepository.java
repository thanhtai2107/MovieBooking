package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.entity.MovieDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieDateRepository extends JpaRepository<MovieDate, MovieDate> {
}
