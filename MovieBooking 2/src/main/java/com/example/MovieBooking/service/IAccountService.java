package com.example.MovieBooking.service;

import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.entity.Account;

public interface IAccountService {
    void register(AccountReq accountRegister);
    Account findUserByUsername(String username);

}
