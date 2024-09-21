package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.MovieDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieDateRepository extends JpaRepository<MovieDate, Long> {
    Optional<MovieDate> findByMovie_MovieIdAndShowDate_ShowDateId(Long movieId, Long showDateId);
    void deleteByMovie_MovieIdAndShowDate_ShowDateId(Long movieId, Long showDateId);

    // You can add more custom query methods here if needed
    // For example:
    // List<MovieDate> findByMovie_MovieId(Long movieId);
    // List<MovieDate> findByShowDate_ShowDateId(Long showDateId);
}
