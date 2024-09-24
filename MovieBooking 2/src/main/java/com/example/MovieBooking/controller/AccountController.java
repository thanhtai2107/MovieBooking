package com.example.MovieBooking.controller;

import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.service.IAccountService;
import com.example.MovieBooking.util.AccountRegisterValidate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        accountRegisterValidate.validate(account, bindingResult);
        if(bindingResult.hasErrors()){
            System.out.println("co loi");
            return "register";
        }
        accountService.register(account);
        return "redirect:/login";
    }
}
