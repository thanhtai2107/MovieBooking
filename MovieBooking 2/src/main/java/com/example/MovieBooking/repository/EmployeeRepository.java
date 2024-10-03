package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "from Employee e where  e.account.status = 1 and e.account.username like %?1%")
    Page<Employee>  findAll(String username, Pageable pageable);
}
