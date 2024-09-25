package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, Long> {
    @Query("SELECT s FROM SeatType s WHERE s.seatTypeName = :name")
    SeatType findByName(@Param("name") String name);
}
