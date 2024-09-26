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

@Service
@Transactional
public class SeatServiceImpl implements ISeatService {
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatTypeRepository seatTypeRepository;

    @Override
    public List<Seat> listSeatByCinemaRoomId(Long cinemaRoomId) {
        return seatRepository.findByCinemaRoomId(cinemaRoomId);
    }

    @Override
    public Seat getSeatById(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(() -> new IllegalArgumentException("Invalid seat Id:" + seatId));
    }

    @Override
    public void updateListSeatType(Long[] listSeatIds, String valueSeatType) {
        SeatType seatType = seatTypeRepository.findByName(valueSeatType);
        for (Long seatId : listSeatIds) {
            Seat seat = getSeatById(seatId);
            seat.setSeatType(seatType);
            seatRepository.save(seat);
        }
    }

}
