package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookingService {
    Page<Booking> getBookingsPagination(String searchInput, int page, int size );
}
