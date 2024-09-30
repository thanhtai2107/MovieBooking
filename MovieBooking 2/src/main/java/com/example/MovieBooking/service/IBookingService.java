package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.Booking;
import org.springframework.data.domain.Page;


import java.time.LocalDate;
import java.util.List;

public interface IBookingService {
    Page<Booking> getBookingsPagination(String searchInput, int page, int size );
    Page<Booking> getBookingsAddedScoreByDate(Long id, LocalDate fromDate, LocalDate toDate, int page, int size);
    Page<Booking> getBookingsUsedScoreByDate(Long id, LocalDate fromDate, LocalDate toDate, int page, int size);
}
