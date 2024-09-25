package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.BookingSeat;

import java.util.List;

public interface IBookingSeatService {
    // Lấy danh sách các ghế đã được đặt dựa trên movieId và scheduleId
    public List<BookingSeat> getBookedSeats(Long movieId, Long scheduleId);
}
