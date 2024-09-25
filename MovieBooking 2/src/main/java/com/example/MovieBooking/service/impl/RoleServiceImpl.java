package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.Role;
import com.example.MovieBooking.repository.RoleRepository;
import com.example.MovieBooking.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByRoleName(name);
    }
}
