package com.example.MovieBooking.controller;


import com.example.MovieBooking.entity.*;
import com.example.MovieBooking.service.impl.BookingSeatServiceImpl;
import com.example.MovieBooking.service.impl.CinemaRoomServiceImpl;
import com.example.MovieBooking.service.impl.MovieServiceImpl;
import com.example.MovieBooking.service.impl.SeatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class BookingSeatController{
    @Autowired 
    private CinemaRoomServiceImpl cinemaRoomService;
    
    @Autowired
    private SeatServiceImpl seatService;
    
    @Autowired
    private MovieServiceImpl movieService;
    
    @Autowired
    private BookingSeatServiceImpl bookingSeatService;

    @GetMapping("/select-seat")
    public String showSelectSeat(
            @RequestParam("movieId") Long movieId,
            @RequestParam("scheduleId") Long scheduleId,
            Model model) {

        // Lấy phòng chiếu từ movieId
        Movie movie = movieService.getMovieById(movieId);
        Long cinemaRoomId = movie.getCinemaRoom().getCinemaRoomId();
        

//         Lấy danh sách ghế từ cinemaRoomId
        List<Seat> seats = seatService.listSeatByCinemaRoomId(cinemaRoomId);

        // Lấy danh sách ghế đã được đặt dựa trên movieId và scheduleId
        List<BookingSeat> bookingSeats = bookingSeatService.getBookedSeats(movieId, scheduleId );

        // Đánh dấu các ghế đã được đặt (ví dụ, đặt trạng thái 'sold' hoặc 1)
        for (BookingSeat bookingSeat : bookingSeats) {
            for(Seat seat : seats) {
                if(seat.getSeatId().equals(bookingSeat.getSeat().getSeatId())) {
                    seat.setSeatStatus(1);// Giả sử trạng thái 1 là ghế đã được đặt
                }
            }
        }
        
        // Đếm số ghế còn trống
        int availableSeatsCount = (int) seats.stream().filter(seat -> seat.getSeatStatus() == 0).count();

        // Tạo danh sách số lượng ghế có thể chọn
        List<Integer> seatQuantities = new ArrayList<>();
        for (int i1 = 1; i1 <= availableSeatsCount; i1++) {
            Integer integer = i1;
            seatQuantities.add(integer);
        }

        // Tạo danh sách hàng ghế
        List<List<Seat>> rows = new ArrayList<>();
        for (int i = 0; i < seats.size(); i += 6) {
            int end = Math.min(i + 6, seats.size());
            rows.add(seats.subList(i, end));
        }
        model.addAttribute("seats", bookingSeats ); // Tất cả ghế
        model.addAttribute("availableSeatsCount", availableSeatsCount); // Số ghế trống
        model.addAttribute("seatQuantities", seatQuantities); // Danh sách số lượng ghế
        model.addAttribute("seatRows", rows);
        model.addAttribute("movieId", movieId);
        model.addAttribute("scheduleId", scheduleId);

        return "TKS-selecttingseat";
    }



}
