package com.example.MovieBooking.service;

import com.example.MovieBooking.dto.req.AccountDTO;
import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IAccountService extends UserDetailsService {
    void register(AccountReq accountRegister);
    Account findUserByUsername(String username);
    Account findUserById(Long id);
    void updateAccount(AccountReq account, MultipartFile imageUrl) throws IOException;
    AccountDTO getMemberById(Long id);
    AccountDTO getMemberByIdentityCard(String identityCard);
}
