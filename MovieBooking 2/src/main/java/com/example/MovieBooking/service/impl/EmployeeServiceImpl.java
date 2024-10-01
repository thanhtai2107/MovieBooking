package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.entity.Account;
import com.example.MovieBooking.entity.Employee;
import com.example.MovieBooking.entity.Role;
import com.example.MovieBooking.repository.AccountRepository;
import com.example.MovieBooking.repository.EmployeeRepository;
import com.example.MovieBooking.service.IAccountService;
import com.example.MovieBooking.service.IEmployeeService;
import com.example.MovieBooking.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {


    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public List<Employee> getALl() {
        return employeeRepository.findAll();
    }

    @Override
    public Page<Employee> getAll(String username,Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,1);
        return employeeRepository.findAll(username,pageable);
    }

    @Override
    public void add(AccountReq account) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Role role = roleService.findRoleByName("EMPLOYEE");
        Account account1 = Account.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .address(account.getAddress())
                .gender(account.getGender())
                .dateOfBirth(LocalDate.parse(account.getDateOfBirth(), formatter))
                .fullname(account.getFullname())
                .email(account.getEmail())
                .identityCard(account.getIdentityCard())
                .phoneNumber(account.getPhoneNumber())
                .status(1)
                .role(role)
                .registerDate(LocalDate.now())
                .build();

        accountRepository.save(account1);
        Employee employee = new Employee(null,account1);
        employeeRepository.save(employee);
    }
}
