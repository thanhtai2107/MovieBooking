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

@Service
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Page<Booking> getBookingByUserName(Pageable page,String userName) {
        Page<Booking> bookingPage =bookingRepository.findBookingByUserName(userName ,page);
        return bookingPage;
    }

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
        int useScore = booking.getUseScore();
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


    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking updateBooking(Booking booking){
        return bookingRepository.save(booking);
    }
}
