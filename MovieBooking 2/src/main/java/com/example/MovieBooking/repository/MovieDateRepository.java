package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.MovieDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDateRepository extends JpaRepository<MovieDate, Long> {
    
    @Modifying
    @Query("DELETE FROM MovieDate md WHERE md.movie.movieId = :movieId")
    void deleteByMovieId(@Param("movieId") Long movieId);
}
