package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.repository.MovieRepository;
import com.example.MovieBooking.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl  implements IMovieService {
    @Autowired
    private MovieRepository movieRepository;


    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> findMovieCustom(String searchInput) {
        List<Movie> movies = movieRepository.findMoviesCustom(searchInput);
        return movies;
    }

    @Override
    public List<Movie> getMoviesByDate(LocalDate date) {
        return movieRepository.findMoviesByDate(date);
    }


}
//=======
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//public class MovieServiceImpl implements IMovieService {
//    @Autowired
//    private MovieRepository movieRepository;
//
//
//
//>>>>>>> 991d711644320553baeeb1dc78afbd40f1ea4081
//}
