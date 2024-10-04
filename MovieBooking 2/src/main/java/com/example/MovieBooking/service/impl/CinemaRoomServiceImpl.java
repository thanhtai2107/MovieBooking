package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.CinemaRoom;
import com.example.MovieBooking.repository.CinemaRoomRepository;
import com.example.MovieBooking.service.ICinemaRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.MovieBooking.entity.Seat;
import com.example.MovieBooking.entity.SeatType;
import com.example.MovieBooking.repository.CinemaRoomRepository;
import com.example.MovieBooking.repository.SeatRepository;
import com.example.MovieBooking.repository.SeatTypeRepository;
import com.example.MovieBooking.service.ICinemaRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import java.util.List;

@Service
@Transactional
public class CinemaRoomServiceImpl implements ICinemaRoomService {
    @Autowired
    private CinemaRoomRepository cinemaRoomRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatTypeRepository seatTypeRepository;

    @Override
    public List<CinemaRoom> getAllCinemaRooms() {
        return cinemaRoomRepository.findAll();
    }

    @Override
    public Page<CinemaRoom> getCinemaRoomsByName(String valueSearch, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cinemaRoomRepository.findByCinemaRoomName(valueSearch, pageable);
    }

    @Override
    public Page<CinemaRoom> getCinemaRoomsByIdOrBySeatQuantity(Long valueSearch, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return cinemaRoomRepository.findByCinemaRoomIdOrSeatQuantity(valueSearch, pageable);
    }


    @Override
    public CinemaRoom getCinemaRoomById(Long cinemaRoomId){
        Optional<CinemaRoom> findCinemaRoom = cinemaRoomRepository.findById(cinemaRoomId);
        return findCinemaRoom.orElse(null);
    }

    @Override
    public boolean existsByCinemaRoomName(String cinemaRoomName) {
        return cinemaRoomRepository.existsByCinemaName(cinemaRoomName);
    }

    @Override
    public void saveCinemaRoom(CinemaRoom cinemaRoom) {
        CinemaRoom cinemaRoomSaved = cinemaRoomRepository.save(cinemaRoom);
        generateSeats(cinemaRoomSaved);
    }

    private void generateSeats(CinemaRoom cinemaRoom) {
        SeatType seatType = seatTypeRepository.findByName("normal");
        int seatQuantity = cinemaRoom.getSeatQuantity();
        String[] columns = {"A", "B", "C", "D", "E", "F"};
        int row = 1;
        int seatIndex = 0;

        for (int i = 1; i <= seatQuantity; i++) {
            Seat seat = new Seat();

            // Set the column (A, B, C, D, E, F)
            seat.setSeatColumn(columns[seatIndex % 6]);

            // Set the row (increments after every 6 columns)
            seat.setSeatRow(row);

            // Default seat settings
            seat.setSeatStatus(0);  // Available by default
            seat.setSeatType(seatType); // Normal type by default
            seat.setCinemaRoom(cinemaRoom);  // Associate with cinemaRoom

            // Save the seat
            seatRepository.save(seat);

            // Update seatIndex and row for next seat
            seatIndex++;
            if (seatIndex % 6 == 0) {
                row++;  // Move to the next row after every 6 columns
            }
        }
    }


}
