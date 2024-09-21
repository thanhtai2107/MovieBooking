package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.repository.CinemaRoomRepository;
import com.example.MovieBooking.service.ICinemaRoomService;
import com.example.MovieBooking.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.MovieBooking.entity.CinemaRoom;
import com.example.MovieBooking.service.ICinemaRoomService;

import java.util.List;


@Service
public class CinemaRoomServiceImpl implements ICinemaRoomService {

    private final CinemaRoomRepository cinemaRoomRepository;

    @Autowired
    public CinemaRoomServiceImpl(CinemaRoomRepository cinemaRoomRepository) {
        this.cinemaRoomRepository = cinemaRoomRepository;
    }

    @Override
    public List<CinemaRoom> getAllCinemaRooms() {
        return cinemaRoomRepository.findAll();
    }

    @Override
    public CinemaRoom getCinemaRoomById(Long id) {
        return cinemaRoomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CinemaRoom not found with id: " + id));
    }

    @Override
    public CinemaRoom saveCinemaRoom(CinemaRoom cinemaRoom) {
        return cinemaRoomRepository.save(cinemaRoom);
    }

    @Override
    public void deleteCinemaRoom(Long id) {
        cinemaRoomRepository.deleteById(id);
    }
}
