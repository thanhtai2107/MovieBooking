package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.CinemaRoom;
import com.example.MovieBooking.repository.CinemaRoomRepository;
import com.example.MovieBooking.service.ICinemaRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CinemaRoomServiceImpl implements ICinemaRoomService {

    @Autowired
    private CinemaRoomRepository cinemaRoomRepository;

    @Override
    public List<CinemaRoom> getAllCinemaRooms() {
        return cinemaRoomRepository.findAll();
    }

}
