package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTypeRepository extends JpaRepository<MovieType, Long> {
}
