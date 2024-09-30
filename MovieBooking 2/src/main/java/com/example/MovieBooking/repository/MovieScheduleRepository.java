package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.MovieSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, Long> {
	
	@Modifying
	@Transactional
	@Query("DELETE FROM MovieSchedule ms WHERE ms.movie.movieId = :movieId")
	void deleteByMovieId(@Param("movieId") Long movieId);
}
