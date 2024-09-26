package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.Employee;
import com.example.MovieBooking.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping("/list")
    public String showListEmployee(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {

        Page<Employee> list = employeeService.getAll(pageNo);
//        List<Employee> employeeList = employeeService.getALl();
        model.addAttribute("list", list);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "employee/list";
    }

    @GetMapping("/add")
    public String showFormAdd(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/add";
    }
}