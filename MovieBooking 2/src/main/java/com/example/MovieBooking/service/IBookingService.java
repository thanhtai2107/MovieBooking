package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.Booking;
import org.springframework.data.domain.Page;


import java.time.LocalDate;
import java.util.List;
/**
 * Interface for booking services, providing methods for managing and retrieving booking records.
 *
 * @author Doan Minh Phong
 */

public interface IBookingService {

    /**
     * Retrieves a paginated list of bookings for a specific user, optionally filtered by a search input.
     *
     * @param id          The user ID for which the bookings are to be retrieved.
     * @param searchInput The search input to filter the bookings (optional).
     * @param page        The page number for pagination.
     * @param size        The number of records per page.
     * @return A Page containing Booking objects matching the search criteria.
     */
    Page<Booking> getBookingsPagination(Long id, String searchInput, int page, int size);

    // Other possible method
    // /**
    //  * Retrieves a paginated list of bookings, optionally filtered by a search input.
    //  * 
    //  * @param searchInput The search input to filter the bookings (optional).
    //  * @param page        The page number for pagination.
    //  * @param size        The number of records per page.
    //  * @return A Page containing Booking objects matching the search criteria.
    //  */
    // Page<Booking> getBookingsPagination(String searchInput, int page, int size);

    /**
     * Saves a booking record to the database.
     *
     * @param booking The Booking object to be saved.
     * @return The saved Booking object.
     */
    Booking saveBooking(Booking booking);

    Page<Booking> getBookingsAddedScoreByDate(Long id, LocalDate fromDate, LocalDate toDate, int page, int size);
    Page<Booking> getBookingsUsedScoreByDate(Long id, LocalDate fromDate, LocalDate toDate, int page, int size);
}
