package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.entity.Account;
import com.example.MovieBooking.entity.Member;
import com.example.MovieBooking.entity.Role;
import com.example.MovieBooking.repository.AccountRepository;
import com.example.MovieBooking.service.IAccountService;
import com.example.MovieBooking.service.IMemberService;
import com.example.MovieBooking.service.IRoleService;
import com.example.MovieBooking.service.IUploadImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IUploadImage uploadImage;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isPresent()) {
            Account account1 = account.get();
            return Account.builder()
                    .username(account1.getUsername())
                    .password(account1.getPassword())
                    .role(account1.getRole())
                    .build();
        } else
            throw new UsernameNotFoundException("User not found");
    }

    @Override
    public Account findUserById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public void updateAccount(AccountReq account, MultipartFile imageUrl) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Account account1 = findUserById(account.getId());
        account1.setPassword(account.getPassword());
        account1.setFullname(account.getFullname());
        account1.setDateOfBirth(LocalDate.parse(account.getDateOfBirth(), formatter));
        account1.setGender(account.getGender());
        account1.setIdentityCard(account.getIdentityCard());
        account1.setPhoneNumber(account.getPhoneNumber());
        account1.setAddress(account.getAddress());
        account1.setEmail(account.getEmail());
        if (!imageUrl.isEmpty()) {
            account1.setImage(uploadImage.uploadImage(imageUrl));
        }
        accountRepository.save(account1);
    }
}
