package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.*;
import com.example.MovieBooking.repository.*;
import com.example.MovieBooking.service.IMovieService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.repository.MovieRepository;
import com.example.MovieBooking.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieServiceImpl implements IMovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieTypeRepository movieTypeRepository;

    @Autowired
    private MovieScheduleRepository movieScheduleRepository;

    @Autowired
    private MovieDateRepository movieDateRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    @Override
    @Transactional
    public Movie saveMovie(Movie movie, List<Long> typeIds, List<Long> scheduleIds) {
        // Check if a movie with the same name already exists
        if (movieRepository.existsByNameEnglishOrNameVN(movie.getNameEnglish(), movie.getNameVN())) {
            throw new IllegalArgumentException("A movie with this name already exists");
        }

        Movie savedMovie = movieRepository.save(movie);
        
        if (typeIds != null && !typeIds.isEmpty()) {
            List<MovieType> movieTypes = typeIds.stream()
                .map(typeId -> {
                    Type type = typeRepository.findById(typeId)
                        .orElseThrow(() -> new RuntimeException("Type not found"));
                    MovieType movieType = new MovieType();
                    movieType.setMovie(savedMovie);
                    movieType.setType(type);
                    return movieType;
                })
                .collect(Collectors.toList());
            savedMovie.setMovieTypeList(movieTypes);
        }
        
        if (scheduleIds != null && !scheduleIds.isEmpty()) {
            List<MovieSchedule> movieSchedules = scheduleIds.stream()
                .map(scheduleId -> {
                    Schedule schedule = scheduleRepository.findById(scheduleId)
                        .orElseThrow(() -> new RuntimeException("Schedule not found"));
                    MovieSchedule movieSchedule = new MovieSchedule();
                    movieSchedule.setMovie(savedMovie);
                    movieSchedule.setSchedule(schedule);
                    return movieSchedule;
                })
                .collect(Collectors.toList());
            savedMovie.setMovieScheduleList(movieSchedules);
        }
        
        return movieRepository.save(savedMovie);
    }

    @Override
    @Transactional
    public Movie updateMovie(Long id, Movie updatedMovie, List<Long> typeIds, List<Long> scheduleIds) {
        Movie existingMovie = movieRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie not found"));

        updateBasicFields(existingMovie, updatedMovie);

        if (typeIds != null) {
            updateTypes(existingMovie, typeIds);
        }
        if (scheduleIds != null) {
            updateSchedules(existingMovie, scheduleIds);
        }

        Movie savedMovie = movieRepository.save(existingMovie);
        logger.info("Movie updated successfully: {}", savedMovie);
        return savedMovie;
    }

    private void updateBasicFields(Movie existingMovie, Movie updatedMovie) {
        existingMovie.setNameVN(updatedMovie.getNameVN());
        existingMovie.setNameEnglish(updatedMovie.getNameEnglish());
        existingMovie.setFromDate(updatedMovie.getFromDate());
        existingMovie.setToDate(updatedMovie.getToDate());
        existingMovie.setActor(updatedMovie.getActor());
        existingMovie.setContent(updatedMovie.getContent());
        existingMovie.setDirector(updatedMovie.getDirector());
        existingMovie.setDuration(updatedMovie.getDuration());
        existingMovie.setLargeImage(updatedMovie.getLargeImage());
        existingMovie.setProductionCompany(updatedMovie.getProductionCompany());
        existingMovie.setVersion(updatedMovie.getVersion());
    }

    private void updateTypes(Movie movie, List<Long> typeIds) {
        if (movie.getMovieTypeList() == null) {
            movie.setMovieTypeList(new ArrayList<>());
        }
        
        // Remove types that are no longer selected
        movie.getMovieTypeList().removeIf(movieType -> 
            typeIds == null || !typeIds.contains(movieType.getType().getTypeId()));
        
        // Add new types
        if (typeIds != null) {
            for (Long typeId : typeIds) {
                if (movie.getMovieTypeList().stream().noneMatch(mt -> mt.getType().getTypeId().equals(typeId))) {
                    Type type = typeRepository.findById(typeId)
                        .orElseThrow(() -> new RuntimeException("Type not found"));
                    MovieType movieType = new MovieType();
                    movieType.setMovie(movie);
                    movieType.setType(type);
                    movie.getMovieTypeList().add(movieType);
                }
            }
        }
    }

    private void updateSchedules(Movie movie, List<Long> scheduleIds) {
        if (movie.getMovieScheduleList() == null) {
            movie.setMovieScheduleList(new ArrayList<>());
        }
        
        // Remove schedules that are no longer selected
        movie.getMovieScheduleList().removeIf(movieSchedule -> 
            !scheduleIds.contains(movieSchedule.getSchedule().getScheduleId()));
        
        // Add new schedules
        for (Long scheduleId : scheduleIds) {
            if (movie.getMovieScheduleList().stream().noneMatch(ms -> ms.getSchedule().getScheduleId().equals(scheduleId))) {
                Schedule schedule = scheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));
                MovieSchedule movieSchedule = new MovieSchedule();
                movieSchedule.setMovie(movie);
                movieSchedule.setSchedule(schedule);
                movie.getMovieScheduleList().add(movieSchedule);
                movieScheduleRepository.save(movieSchedule);  // Add this line
            }
        }
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        logger.info("Deleting movie with id: {}", id);
        Movie movie = movieRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie not found"));
        movieRepository.delete(movie);
        logger.info("Movie deleted successfully");
    }

    @Override
    public List<Movie> searchMovies(String query) {
        String lowercaseQuery = query.toLowerCase();
        return movieRepository.findAll().stream()
                .filter(movie -> 
                    movie.getNameEnglish().toLowerCase().contains(lowercaseQuery) ||
                    movie.getNameVN().toLowerCase().contains(lowercaseQuery) ||
                    movie.getActor().toLowerCase().contains(lowercaseQuery) ||
                    movie.getDirector().toLowerCase().contains(lowercaseQuery) ||
                    movie.getProductionCompany().toLowerCase().contains(lowercaseQuery) ||
                    movie.getVersion().toLowerCase().contains(lowercaseQuery) ||
                    movie.getContent().toLowerCase().contains(lowercaseQuery) ||
                    movie.getMovieTypeList().stream().anyMatch(movieType -> 
                        movieType.getType().getTypeName().toLowerCase().contains(lowercaseQuery))
                )
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Movie getMovieByIdWithSchedules(Long id) {
        return movieRepository.findByIdWithSchedules(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Movie getMovieWithTypesAndSchedules(Long id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie != null) {
            Hibernate.initialize(movie.getMovieTypeList());
            Hibernate.initialize(movie.getMovieScheduleList());
        }
        return movie;
    }
  
    public List<Movie> findMovieCustom(String searchInput) {
        List<Movie> movies = movieRepository.findMoviesCustom(searchInput);
        return movies;
    }

    @Override
    public List<Movie> getMoviesByDate(LocalDate date) {
        return movieRepository.findMoviesByDate(date);
    }

    public Movie getMovieById(Long id) {
//        Long longId = Long.valueOf(id);
        return movieRepository.findById(id).get();
    }

}

