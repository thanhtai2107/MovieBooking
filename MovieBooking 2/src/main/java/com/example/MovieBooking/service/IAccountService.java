package com.example.MovieBooking.service;

import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountService extends UserDetailsService {
    void register(AccountReq accountRegister);
    Account findUserByUsername(String username);

}
