package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.Booking;
import com.example.MovieBooking.repository.BookingRepository;
import com.example.MovieBooking.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements IBookingService {


    @Autowired
    BookingRepository bookingRepository;

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Page<Booking> getBookingsPagination(String searchInput, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookingRepository.find(searchInput,pageable);
    }

    @Override
    public Page<Booking> getBookingsAddedScoreByDate(Long id, LocalDate fromDate, LocalDate toDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookingRepository.findAddedScoreByDate(id,fromDate,toDate,pageable);
    }

    @Override
    public Page<Booking> getBookingsUsedScoreByDate(Long id, LocalDate fromDate, LocalDate toDate, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return bookingRepository.findUsedScoreByDate(id, fromDate,toDate,pageable);
    }



}

