package com.example.MovieBooking.controller;

import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.entity.Account;
import com.example.MovieBooking.service.IAccountService;
import com.example.MovieBooking.util.AccountRegisterValidate;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Controller
public class AccountController{

    @Autowired
    private IAccountService accountService;

    @Autowired
    private AccountRegisterValidate accountRegisterValidate;


    @GetMapping("/register")
    public String register( Model model){
        AccountReq account = new AccountReq();
        account.setGender("Nam");
        model.addAttribute("account", account);
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("account") AccountReq account, BindingResult bindingResult, Model model){
        Account account1 = accountService.findUserByUsername(account.getUsername());
        if (account1 != null){
            bindingResult.rejectValue("username", "Account already exists");
        }
        accountRegisterValidate.validate(account, bindingResult);
        if(bindingResult.hasErrors()){
            return "register";
        }
        accountService.register(account);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/edit")
    public String edit(Model model, @AuthenticationPrincipal Account account){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Account account1 = accountService.findUserByUsername(account.getUsername());
        AccountReq accountEdit = AccountReq.builder()
                .id(account1.getAccountId())
                .username(account1.getUsername())
                .password(account1.getPassword())
                .address(account1.getAddress())
                .dateOfBirth(account1.getDateOfBirth().format(formatter))
                .email(account1.getEmail())
                .fullname(account1.getFullname())
                .gender(account1.getGender())
                .identityCard(account1.getIdentityCard())
                .phoneNumber(account1.getPhoneNumber())
                .build();
        System.out.println(accountEdit.toString());
        model.addAttribute("account", accountEdit);
        model.addAttribute("image", account1.getImage());
        return "edit-account";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("account") AccountReq account, @RequestParam(value = "image", required = false) MultipartFile image, BindingResult bindingResult, Model model, HttpSession session) throws IOException {
        accountRegisterValidate.validate(account, bindingResult);
        if(bindingResult.hasErrors()){
            return "edit-account";
        }
        accountService.updateAccount(account, image);
        Account account1 = accountService.findUserByUsername(account.getUsername());
        session.setAttribute("account", account1);
        return "redirect:/edit";
    }
}
