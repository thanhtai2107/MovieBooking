package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.CinemaRoom;
import com.example.MovieBooking.repository.CinemaRoomRepository;
import com.example.MovieBooking.service.ICinemaRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.MovieBooking.entity.Seat;
import com.example.MovieBooking.entity.SeatType;
import com.example.MovieBooking.repository.SeatRepository;
import com.example.MovieBooking.repository.SeatTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
* @Account: Nguyen Cong Van
* @BirthDate: 2001/04/01
* @Desc: Service implementation for managing CinemaRoom entities.
* This service provides methods for retrieving, saving, and generating seats for cinema rooms.
* */
@Service
@Transactional
public class CinemaRoomServiceImpl implements ICinemaRoomService {
    @Autowired
    private CinemaRoomRepository cinemaRoomRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatTypeRepository seatTypeRepository;


    /**
     * @return List all cinema rooms from the database.
     */
    @Override
    public List<CinemaRoom> getAllCinemaRooms() {
        return cinemaRoomRepository.findAll();
    }

    /**
     * Retrieves a paginated list of cinema rooms filtered by the name.
     *
     * @param valueSearch the cinema room name to search for.
     * @param page the page number to retrieve.
     * @param size the number of records per page.
     * @return list cinema rooms with names matching the search criteria.
     */
    @Override
    public Page<CinemaRoom> getCinemaRoomsByName(String valueSearch, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cinemaRoomRepository.findByCinemaRoomName(valueSearch, pageable);
    }

    /**
     * Retrieves a paginated list of cinema rooms filtered by ID or seat quantity.
     *
     * @param valueSearch the ID or seat quantity to search for.
     * @param page the page number to retrieve.
     * @param size the number of records per page.
     * @return list cinema rooms with matching ID or seat quantity.
     */
    @Override
    public Page<CinemaRoom> getCinemaRoomsByIdOrBySeatQuantity(Long valueSearch, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return cinemaRoomRepository.findByCinemaRoomIdOrSeatQuantity(valueSearch, pageable);
    }

    /**
     * Retrieves a specific cinema room by its ID.
     *
     * @param cinemaRoomId the ID of the cinema room to retrieve.
     * @return a Cinema Room by ID
     */
    @Override
    public CinemaRoom getCinemaRoomById(Long cinemaRoomId){
        Optional<CinemaRoom> findCinemaRoom = cinemaRoomRepository.findById(cinemaRoomId);
        return findCinemaRoom.orElse(null);
    }

    /**
     * Checks if a cinema room with the given name exists in the database.
     *
     * @param cinemaRoomName the name of the cinema room to check.
     * @return {true} if a cinema room with the given name exists, otherwise {false}.
     */
    @Override
    public boolean existsByCinemaRoomName(String cinemaRoomName) {
        return cinemaRoomRepository.existsByCinemaName(cinemaRoomName);
    }

    /**
     * Saves a cinema room to the database and automatically generates seats for the room.
     *
     * @param cinemaRoom the CinemaRoom entity to save.
     */
    @Override
    public void saveCinemaRoom(CinemaRoom cinemaRoom) {
        CinemaRoom cinemaRoomSaved = cinemaRoomRepository.save(cinemaRoom);
        // After save a Cinema Room. auto add seat
        generateSeats(cinemaRoomSaved);
    }

    /**
     * Generates default seats for a given cinema room. Seats are arranged in rows and columns,
     * with a default seat type of "normal" and seat status of available.
     *
     * @param cinemaRoom the  CinemaRoom entity for which seats are being generated.
     */
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
