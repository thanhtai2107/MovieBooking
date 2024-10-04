package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.Seat;
import com.example.MovieBooking.entity.SeatType;
import com.example.MovieBooking.repository.SeatRepository;
import com.example.MovieBooking.repository.SeatTypeRepository;
import com.example.MovieBooking.service.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
* @Account: Nguyen Cong Van
* @BirthDate: 2001/04/01
* @Desc: Service implementation for managing Seat entities.
* This service provides methods for retrieving, updating, and managing seat types for specific cinema rooms.
* */
@Service
@Transactional
public class SeatServiceImpl implements ISeatService {
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatTypeRepository seatTypeRepository;

    /**
     * Retrieves a list of seats associated with a specific cinema room.
     *
     * @param cinemaRoomId the ID of the cinema room to retrieve seats for.
     * @return a list of Seat entities associated with the given cinema room ID.
     */
    @Override
    public List<Seat> listSeatByCinemaRoomId(Long cinemaRoomId) {
        return seatRepository.findByCinemaRoomId(cinemaRoomId);
    }

    /**
     * Retrieves a seat by its ID.
     *
     * @param seatId the ID of the seat to retrieve.
     * @return the Seat entity if found.
     * @throws IllegalArgumentException if the seat ID is invalid or the seat does not exist.
     */
    @Override
    public Seat getSeatById(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(() -> new IllegalArgumentException("Invalid seat Id:" + seatId));
    }

    /**
     * Updates the seat type for a list of seats based on their IDs.
     *
     * @param listSeatIds an array of seat IDs to update.
     * @param valueSeatType the name of the seat type to assign to the seats.
     */
    @Override
    public void updateListSeatType(Long[] listSeatIds, String valueSeatType) {
        SeatType seatType = seatTypeRepository.findByName(valueSeatType);
        for (Long seatId : listSeatIds) {
            Seat seat = getSeatById(seatId);
            seat.setSeatType(seatType);
            seatRepository.save(seat);
        }
    }

    /**
     * Retrieves a list of seats by their IDs.
     *
     * @param selectedSeatIds a list of seat IDs to retrieve.
     * @return a list of Seat entities with the specified IDs.
     */
    @Override
    public List<Seat> getSeatSByIds(List<Long> selectedSeatIds) {
        return seatRepository.findBySeatIdIn(selectedSeatIds);
    }

}
