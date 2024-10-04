package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.ShowDate;
import com.example.MovieBooking.repository.ShowDateRepository;
import com.example.MovieBooking.service.IShowDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class ShowDateServiceImpl implements IShowDateService {
    @Autowired
    private ShowDateRepository showDateRepository;
    @Override
    public ShowDate findShowDateByDate(LocalDate date) {
        return showDateRepository.findByShowDate(date);
    }
}
