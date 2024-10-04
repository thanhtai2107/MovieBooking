package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.BookingSeat;

import java.time.LocalDate;
import java.util.List;
/**
 * Interface for booking seat services, providing methods to manage and retrieve booked seat information.
 *
 * @author Doan Minh Phong
 */
public interface IBookingSeatService {

    /**
     * Retrieves a list of seats that have been booked for a specific movie, schedule, and show date.
     *
     * @param movieId    The ID of the movie.
     * @param scheduleId The ID of the schedule.
     * @param showDate   The date of the show.
     * @return A list of BookingSeat objects representing the seats that have already been booked.
     */
    public List<BookingSeat> getBookedSeats(Long movieId, Long scheduleId, LocalDate showDate);
}

