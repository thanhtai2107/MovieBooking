package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.Booking;
import org.springframework.data.domain.Page;


import java.time.LocalDate;
import java.util.List;

public interface IBookingService {
    List<Booking> findAll();
    Page<Booking> getBookingsPagination(Long id, String searchInput, int page, int size );
    Page<Booking> getBookingsPagination(Long id,String searchInput, int page, int size );
    Booking saveBooking(Booking booking);
    Page<Booking> getBookingsAddedScoreByDate(Long id, LocalDate fromDate, LocalDate toDate, int page, int size);
    Page<Booking> getBookingsUsedScoreByDate(Long id, LocalDate fromDate, LocalDate toDate, int page, int size);
}
