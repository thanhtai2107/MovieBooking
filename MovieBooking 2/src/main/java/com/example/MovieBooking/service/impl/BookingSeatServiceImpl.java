package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.BookingSeat;
import com.example.MovieBooking.entity.ShowDate;
import com.example.MovieBooking.repository.BookingSeatRepository;
import com.example.MovieBooking.service.IBookingSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
/**
 * Implementation of the IBookingSeatService interface for handling booking seat operations.
 *
 * @author Doan Minh Phong
 */
@Service
public class BookingSeatServiceImpl implements IBookingSeatService {

    @Autowired
    private BookingSeatRepository bookingSeatRepository;

    /**
     * Retrieves a list of booked seats for a specific movie, schedule, and show date.
     *
     * @param movieId    The ID of the movie.
     * @param scheduleId The ID of the schedule.
     * @param showDate   The date of the show.
     * @return A list of BookingSeat objects that represent the booked seats.
     */
    @Override
    public List<BookingSeat> getBookedSeats(Long movieId, Long scheduleId, LocalDate showDate) {
        return bookingSeatRepository.findBookedSeatsByMovieAndScheduleAndShowDate(movieId, scheduleId, showDate);
    }

    /**
     * Saves a BookingSeat object to the database.
     *
     * @param bookingSeat The BookingSeat object to be saved.
     * @return The saved BookingSeat object.
     */
    public BookingSeat save(BookingSeat bookingSeat) {
        return bookingSeatRepository.save(bookingSeat);
    }
}

