package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("from Movie m where m.actor like %?1% or m.nameVN like %?1% or m.nameEnglish like %?1%" +
            "or m.productionCompany like %?1% or m.content like %?1%")
    List<Movie> findMoviesCustom(String searchInput);

    @Query("SELECT m " +
            "FROM Movie m " +
            "JOIN MovieDate md ON m.movieId = md.movie.movieId " +
            "JOIN ShowDate sd ON md.showDate.showDateId = sd.showDateId " +
            "WHERE sd.showDate = :date")
    List<Movie> findMoviesByDate(@Param("date") LocalDate date);

//    List<Movie> findMoviesByDateAndMovieId(LocalDate date, Long movieId );

}
