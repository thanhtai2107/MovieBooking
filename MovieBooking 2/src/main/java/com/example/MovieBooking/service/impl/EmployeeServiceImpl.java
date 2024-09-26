package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.Employee;
import com.example.MovieBooking.repository.EmployeeRepository;
import com.example.MovieBooking.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public List<Employee> getALl() {
        return employeeRepository.findAll();
    }

    @Override
    public Page<Employee> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,1);
        return employeeRepository.findAll(pageable);
    }
}
