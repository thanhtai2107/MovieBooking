package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.Booking;
import com.example.MovieBooking.entity.BookingSeat;
import com.example.MovieBooking.entity.Seat;
import com.example.MovieBooking.entity.dto.BookingDTO;
import com.example.MovieBooking.repository.BookingRepository;
import com.example.MovieBooking.service.IBookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@Service
public class BookingServiceImpl implements IBookingService {
    @Autowired
    BookingRepository bookingRepository;

    /**
     * @author Nguyen Van Su
     * @param page
     * @param userName
     * @return list booking
     */
    public Page<Booking> getBookingByUserName(Pageable page,String userName) {
        Page<Booking> bookingPage =bookingRepository.findBookingByUserName(userName ,page);
        return bookingPage;
    }

    /**
     * @author Nguyen Van Su
     * @param page
     * @param username
     * @param value
     * @return list booking
     */
    public Page<Booking> getBookingsByConditionWithUser(Pageable page , String username, String value) {
        boolean isMainQuery = true;
        try {
            Long.parseLong(value);
        } catch (Exception e) {
            isMainQuery = false;
        }
        if (isMainQuery) {
          return  bookingRepository.findBookingByConditionsWithUser( username,  Long.parseLong(value),   value,  value, page);
        } else {
           return  bookingRepository.findBookingByIdCAndPhoneNumWithUser( username,  value,  value, page);
        }
    }


    /**
     * @author Nguyen Van Su
     * @param page
     * @param value
     * @return list booking
     */
    public Page<Booking> getBookingsByConditionWithAdmin(Pageable page , String value) {
        boolean isMainQuery = true;
        try {
            Long.parseLong(value);
        } catch (Exception e) {
            isMainQuery = false;
        }
        Page<Booking> bookingPage= null;
        if (isMainQuery) {
            return  bookingPage = bookingRepository.findBookingByConditionsWithAdmin(  Long.parseLong(value),  Long.parseLong(value),  value,  value, page);
        } else {
            return bookingPage = bookingRepository.findBookingByIdCAndPhoneNumWithAdmin(  value, value, page);
        }
    }

    /**
     * @author Nguyen Van Su
     * @param pageableble
     * @return list booking
     */
    public Page<Booking> getBookings(Pageable pageableble) {
        Page<Booking> bookingPage =bookingRepository.findAllBookings(pageableble);
        return bookingPage;
    }

    public BookingDTO convertToBookingDTO(Booking booking) {
        long memberId = booking.getAccount().getMember().getMemberId();
        String fullName = booking.getAccount().getFullname();
        String identityCard = booking.getAccount().getIdentityCard();
        String phoneNumber = booking.getAccount().getPhoneNumber();
        String movie = booking.getMovie().getNameEnglish()+": " +booking.getMovie().getNameVN();
        LocalDate date = booking.getShowDate().getShowDate();
        String time = booking.getSchedule().getScheduleTime();
        int status = booking.getStatus();
        Long useScore = booking.getUseScore();
        String screen = booking.getMovie().getCinemaRoom().getScreen();
        long total = booking.getTotalMoney();
        StringBuilder seatString = new StringBuilder();
        List<BookingSeat> list =booking.getBookingSeatList();
        for (BookingSeat bookingSeat : list) {
            Seat seat = bookingSeat.getSeat();
            if (seatString.toString().isEmpty()) {
                seatString = new StringBuilder(seat.getSeatRow() + seat.getSeatColumn());
            } else {
                seatString.append(" ").append(seat.getSeatRow()).append(seat.getSeatColumn());
            }
        }

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setMemberId(memberId);
        bookingDTO.setFullName(fullName);
        bookingDTO.setIdentityCard(identityCard);
        bookingDTO.setPhoneNumber(phoneNumber);
        bookingDTO.setSeat(seatString.toString());
        bookingDTO.setBookingId(booking.getBookingId());
        bookingDTO.setMovie(movie);
        bookingDTO.setStatus(status);
        bookingDTO.setTime(time);
        bookingDTO.setDate(date);
        bookingDTO.setUseScore(useScore);
        bookingDTO.setScreen(screen);
        bookingDTO.setTotal(total);


        return bookingDTO;
    }

    public List<BookingDTO> convertToBookingDTOList(List<Booking> bookingList) {
        List<BookingDTO> bookingDtoList = new ArrayList<>();
        for (Booking booking : bookingList) {
           BookingDTO bookingDTO =  convertToBookingDTO(booking);

            bookingDtoList.add(bookingDTO);
            System.out.println("-----------------------");
            System.out.println(bookingDTO.toString());
        }

    return bookingDtoList;
    }


    /**
     * @author Nguyen Van Su
     * @param id
     * @return booking
     */
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    /**
     * @author Nguyen Van Su
     * @param booking
     * @return
     */
    public Booking updateBooking(Booking booking){
        return bookingRepository.save(booking);
    }
    
    /**
     * Get all bookings
     *
     * @author Le Thanh Tri
     * @return list booking
     */
    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    /**
     * Get all and search booking information
     *
     * @author Le Thanh Tri
     * @param id
     * @param searchInput
     * @param page
     * @param size
     * @return list booking with pagination
     */
    @Override
    public Page<Booking> getBookingsPagination(Long id, String searchInput, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        boolean isNumeric = searchInput.matches("\\d+");
        if(isNumeric){
            int numericSearch = Integer.parseInt(searchInput);
            return bookingRepository.findWithNumeric(id,numericSearch,pageable);
        }
        return bookingRepository.findWithString(id,searchInput,pageable);
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    /**
     * Get all booking's added score by date
     *
     * @author Le Thanh Tri
     * @param id
     * @param fromDate
     * @param toDate
     * @param page
     * @param size
     * @return list booking added score by date with pagination
     */
    @Override
    public Page<Booking> getBookingsAddedScoreByDate(Long id, LocalDate fromDate, LocalDate toDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookingRepository.findAddedScoreByDate(id,fromDate,toDate,pageable);
    }

    /**
     * Get all booking's used score by date
     *
     * @author Le Thanh Tri
     * @param id
     * @param fromDate
     * @param toDate
     * @param page
     * @param size
     * @return list booking used score by date with pagination
     */
    @Override
    public Page<Booking> getBookingsUsedScoreByDate(Long id, LocalDate fromDate, LocalDate toDate, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return bookingRepository.findUsedScoreByDate(id, fromDate,toDate,pageable);
    }

}

