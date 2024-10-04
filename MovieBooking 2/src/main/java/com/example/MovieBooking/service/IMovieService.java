package com.example.MovieBooking.service;
import com.example.MovieBooking.entity.Movie;
import java.util.List;
import java.time.LocalDate;

/**
 * Interface for Movie service operations.
 *
 * @author Duong Le Phu An
 */
public interface IMovieService {
    /**
     * Retrieves all movies.
     *
     * @return List of all movies
     * @author Duong Le Phu An
     */
    List<Movie> getAllMovies();

    /**
     * Retrieves a movie by its ID.
     *
     * @param id The ID of the movie
     * @return The movie with the specified ID
     * @author Duong Le Phu An
     */
    Movie getMovieById(Long id);

    /**
     * Saves a new movie with associated types and schedules.
     *
     * @param movie The movie to save
     * @param typeIds List of type IDs associated with the movie
     * @param scheduleIds List of schedule IDs associated with the movie
     * @return The saved movie
     * @author Duong Le Phu An
     */
    Movie saveMovie(Movie movie, List<Long> typeIds, List<Long> scheduleIds);

    /**
     * Updates an existing movie with new information and associations.
     *
     * @param id The ID of the movie to update
     * @param updatedMovie The updated movie information
     * @param typeIds List of type IDs to associate with the movie
     * @param scheduleIds List of schedule IDs to associate with the movie
     * @return The updated movie
     * @author Duong Le Phu An
     */
    Movie updateMovie(Long id, Movie updatedMovie, List<Long> typeIds, List<Long> scheduleIds);

    /**
     * Deletes a movie by its ID.
     *
     * @param id The ID of the movie to delete
     * @author Duong Le Phu An
     */
    void deleteMovie(Long id);

    /**
     * Searches for movies based on a query string.
     *
     * @param query The search query
     * @return List of movies matching the search query
     * @author Duong Le Phu An
     */
    List<Movie> searchMovies(String query);

    /**
     * Retrieves a movie by its ID, including its schedules.
     *
     * @param id The ID of the movie
     * @return The movie with the specified ID, including its schedules
     * @author Duong Le Phu An
     */
    Movie getMovieByIdWithSchedules(Long id);

    /**
     * Retrieves a movie by its ID, including its types and schedules.
     *
     * @param id The ID of the movie
     * @return The movie with the specified ID, including its types and schedules
     * @author Duong Le Phu An
     */
    Movie getMovieWithTypesAndSchedules(Long id);

    /**
     * Retrieves movies showing on a specific date.
     *
     * @param date The date to search for movies
     * @return List of movies showing on the specified date
     * @author Duong Le Phu An
     */
    public List<Movie> getMoviesByDate(LocalDate date);
}
