package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MovieTypeRepository extends JpaRepository<MovieType, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM MovieType mt WHERE mt.movie.movieId = :movieId")
    void deleteByMovieId(@Param("movieId") Long movieId);
}
