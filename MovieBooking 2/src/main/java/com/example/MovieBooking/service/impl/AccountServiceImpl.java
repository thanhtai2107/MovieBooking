package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.entity.Account;
import com.example.MovieBooking.entity.Member;
import com.example.MovieBooking.entity.Role;
import com.example.MovieBooking.repository.AccountRepository;
import com.example.MovieBooking.service.IAccountService;
import com.example.MovieBooking.service.IMemberService;
import com.example.MovieBooking.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMemberService memberService;

    @Override
    public void register(AccountReq account) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Role role = roleService.findRoleByName("MEMBER");
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

        Member member = new Member(null, 0, account1);
        memberService.saveMember(member);
    }

    @Override
    public Account findUserByUsername(String username) {
        return accountRepository.findByUsername(username).orElse(null);
    }
}
