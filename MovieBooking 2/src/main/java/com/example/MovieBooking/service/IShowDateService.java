package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.ShowDate;

import java.time.LocalDate;
import java.util.Date;

public interface IShowDateService {
    ShowDate findShowDateByDate(LocalDate date);
}
