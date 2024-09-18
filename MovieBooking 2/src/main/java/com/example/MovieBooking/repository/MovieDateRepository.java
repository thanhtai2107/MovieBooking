package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.MovieDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDateRepository extends JpaRepository<MovieDate, MovieDate> {
}
