package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.Booking;
import org.springframework.data.domain.Page;


public interface IBookingService {
    Page<Booking> getBookingsPagination(String searchInput, int page, int size );
}
