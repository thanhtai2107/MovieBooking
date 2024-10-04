package com.example.MovieBooking.controller;

import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.entity.Account;
import com.example.MovieBooking.service.IAccountService;
import com.example.MovieBooking.util.AccountRegisterValidate;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * @author Hoang Thanh Tai
 */
@Controller
public class AccountController{

    @Autowired
    private IAccountService accountService;

    @Autowired
    private AccountRegisterValidate accountRegisterValidate;


    /**
     * Render register page
     *
     * @author Hoang Thanh Tai
     * @param model supply attributes used for rendering views
     * @return register page
     */
    @GetMapping("/register")
    public String register(Model model){
        AccountReq account = new AccountReq();
        account.setGender("Nam");
        model.addAttribute("account", account);
        return "register";
    }

    /**
     * Register new account
     *
     * @author Hoang Thanh Tai
     * @param account information of user used for register account
     * @param bindingResult hold the result of validation and contain errors that may be occurred
     * @param model supply attributes used for rendering views
     * @return if register success, return login page, else return register page
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("account") AccountReq account, BindingResult bindingResult, Model model){
        Account account1 = accountService.findUserByUsername(account.getUsername());
        if (account1 != null){
            bindingResult.rejectValue("username", null,"Account already exists");
        }
        accountRegisterValidate.validate(account, bindingResult);
        if(bindingResult.hasErrors()){
            return "register";
        }
        accountService.register(account);
        return "redirect:/login";
    }

    /**
     * Render login page
     *
     * @author Hoang Thanh Tai
     * @return login page
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * Get user's information and render to edit-account page
     *
     * @author Hoang Thanh Tai
     * @param model supply attributes used for rendering views.
     * @param account information of authenticated user
     * @return edit-account page
     */
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
        model.addAttribute("account", accountEdit);
        model.addAttribute("image", account1.getImage());
        return "edit-account";
    }

    /**
     * Update user's information
     *
     * @author Hoang Thanh Tai
     * @param account information of user
     * @param image file image received in multipart request, is user's avatar
     * @param bindingResult hold the result of validation and contain errors that may be occurred
     * @param model supply attributes used for rendering views
     * @param session used to store user's information
     * @return edit-account page
     * @throws IOException if file does not exist
     */
    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("account") AccountReq account, @RequestParam(value = "image", required = false) MultipartFile image, BindingResult bindingResult, Model model, HttpSession session) throws IOException {
        accountRegisterValidate.validate(account, bindingResult);
        if(bindingResult.hasErrors()){
            return "edit-account";
        }
        accountService.updateAccount(account, image);
        Account account1 = accountService.findUserByUsername(account.getUsername());
        session.setAttribute("account", account1);
        model.addAttribute("image", account1.getImage());
        model.addAttribute("success", "Update information successfully");
        return "edit-account";
    }
}
