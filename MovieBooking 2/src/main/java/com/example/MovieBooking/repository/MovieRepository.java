package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByNameVNContainingOrNameEnglishContaining(String nameVN, String nameEnglish);
    
    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.movieScheduleList WHERE m.movieId = :id")
    Movie findByIdWithSchedules(@Param("id") Long id);
    
    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.movieTypeList LEFT JOIN FETCH m.movieScheduleList WHERE m.movieId = :id")
    Movie findMovieWithTypesAndSchedules(@Param("id") Long id);
    
    boolean existsByNameEnglishOrNameVN(String nameEnglish, String nameVN);
}