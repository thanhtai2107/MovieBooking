package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEmployeeService {

    List<Employee> getALl();
    Page<Employee> getAll(Integer pageNo);
}
