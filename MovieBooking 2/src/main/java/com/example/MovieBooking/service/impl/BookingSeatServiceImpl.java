package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.BookingSeat;
import com.example.MovieBooking.repository.BookingSeatRepository;
import com.example.MovieBooking.service.IBookingSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingSeatServiceImpl implements IBookingSeatService {
    
    @Autowired
    private BookingSeatRepository bookingSeatRepository;
    
    @Override
    public List<BookingSeat> getBookedSeats(Long movieId, Long scheduleId) {
        return bookingSeatRepository.findBookedSeatsByMovieAndSchedule(movieId,scheduleId);
    }
}
