package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.Account;
import com.example.MovieBooking.repository.AccountRepository;
import com.example.MovieBooking.service.IAccountService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> getAccountByUserName(String userName){
        return accountRepository.findByUsername(userName);
    }

}
