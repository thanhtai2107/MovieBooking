package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieTypeRepository extends JpaRepository<MovieType, Long> {
    Optional<MovieType> findByMovie_MovieIdAndType_TypeId(Long movieId, Long typeId);
    void deleteByMovie_MovieIdAndType_TypeId(Long movieId, Long typeId);

    // You can add more custom query methods here if needed
    // For example:
    // List<MovieType> findByMovie_MovieId(Long movieId);
    // List<MovieType> findByType_TypeId(Long typeId);
}
