package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("from Booking b  where b.account.fullname like %?1% or b.movie.nameVN like %?1% or b.movie.nameEnglish like %?1%")
    Page<Booking> find(String searchInput, Pageable pageable);

    @Query("from Booking b where b.useScore = 0 and b.account.accountId = ?1 and b.bookingDate between ?2 and ?3 ")
    Page<Booking> findAddedScoreByDate(Long id, LocalDate fromDate, LocalDate toDate, Pageable pageable);

    @Query("from Booking b where b.useScore != 0 and  b.account.accountId = ?1 and b.bookingDate between ?2 and ?3 ")
    Page<Booking> findUsedScoreByDate(Long id, LocalDate fromDate, LocalDate toDate, Pageable pageable);
}
