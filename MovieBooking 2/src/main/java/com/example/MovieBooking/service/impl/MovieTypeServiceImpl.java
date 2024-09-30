package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.MovieType;
import com.example.MovieBooking.repository.MovieTypeRepository;
import com.example.MovieBooking.service.IMovieTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieTypeServiceImpl implements IMovieTypeService {

    @Autowired
    private MovieTypeRepository movieTypeRepository;

    @Override
    public MovieType saveMovieType(MovieType movieType) {
        return movieTypeRepository.save(movieType);
    }

    @Override
    public MovieType getMovieTypeById(Long id) {
        return movieTypeRepository.findById(id).orElse(null);
    }

    @Override
    public List<MovieType> getAllMovieTypes() {
        return movieTypeRepository.findAll();
    }

    @Override
    public void deleteMovieType(Long id) {
        movieTypeRepository.deleteById(id);
    }

    @Override
    public void deleteByMovieId(Long movieId) {
        movieTypeRepository.deleteByMovieId(movieId);
    }
}
