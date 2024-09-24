package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("from Booking b  where b.account.fullname like %?1% or b.movie.nameVN like %?1% or b.movie.nameEnglish like %?1%")
    Page<Booking> find(String searchInput, Pageable pageable);
}
