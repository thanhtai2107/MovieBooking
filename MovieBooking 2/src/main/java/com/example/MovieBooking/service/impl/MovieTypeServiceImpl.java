package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.MovieType;
import com.example.MovieBooking.repository.MovieTypeRepository;
import com.example.MovieBooking.service.IMovieTypeService;
import com.example.MovieBooking.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieTypeServiceImpl implements IMovieTypeService {

    private final MovieTypeRepository movieTypeRepository;

    @Autowired
    public MovieTypeServiceImpl(MovieTypeRepository movieTypeRepository) {
        this.movieTypeRepository = movieTypeRepository;
    }

    @Override
    public List<MovieType> getAllMovieTypes() {
        return movieTypeRepository.findAll();
    }

    @Override
    public MovieType getMovieType(Long movieId, Long typeId) {
        return movieTypeRepository.findByMovie_MovieIdAndType_TypeId(movieId, typeId)
                .orElseThrow(() -> new ResourceNotFoundException("MovieType not found with movieId: " + movieId + " and typeId: " + typeId));
    }

    @Override
    public MovieType saveMovieType(MovieType movieType) {
        return movieTypeRepository.save(movieType);
    }

    @Override
    public void deleteMovieType(Long movieId, Long typeId) {
        movieTypeRepository.deleteByMovie_MovieIdAndType_TypeId(movieId, typeId);
    }
}
