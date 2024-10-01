package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.MovieDate;
import com.example.MovieBooking.repository.MovieDateRepository;
import com.example.MovieBooking.service.IMovieDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieDateServiceImpl implements IMovieDateService {

    @Autowired
    private MovieDateRepository movieDateRepository;

    @Override
    public List<MovieDate> getAllMovieDates() {
        return movieDateRepository.findAll();
    }

    @Override
    public MovieDate getMovieDateById(Long id) {
        return movieDateRepository.findById(id).orElse(null);
    }

    @Override
    public MovieDate saveMovieDate(MovieDate movieDate) {
        return movieDateRepository.save(movieDate);
    }

    @Override
    public void deleteMovieDate(Long id) {
        movieDateRepository.deleteById(id);
    }
}
