package com.example.MovieBooking.util;

import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.entity.Account;
import com.example.MovieBooking.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class AccountRegisterValidate implements Validator {

    @Autowired
    private IAccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object object, Errors errors) {
        if (!( object instanceof AccountReq)) {
            return;
        }
        AccountReq accountReq = (AccountReq) object;
        Account account = accountService.findUserByUsername(accountReq.getUsername());

        // Validate username
        String accountRegex = "^[a-zA-Z0-9]{3,}$";
        if (accountReq.getUsername().isBlank()) {
            errors.rejectValue("username", null, "Account is required");
        } else if (!accountReq.getUsername().matches(accountRegex)) {
            errors.rejectValue("username", null,"Account format is incorrect");
        }

        // Validate password
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,}$";
        if (accountReq.getPassword().isBlank()) {
            errors.rejectValue("password", null, "Password is required");
        }else if (!accountReq.getPassword().matches(passwordRegex)) {
            errors.rejectValue("password", null,"Password format is incorrect (Example?123)");
        } else if (account != null) {
            errors.rejectValue("username", null, "Account already exists");
        }

        // Validate confirm password
        if (accountReq.getConfirmPassword().isBlank()) {
            errors.rejectValue("confirmPassword", null, "Confirm password is required");
        } else if(!accountReq.getConfirmPassword().equals(accountReq.getPassword())){
            errors.rejectValue("confirmPassword", null,"Confirm password does not match Password");
        }

        String identityRegex = "^\\d{12}$";
        if (accountReq.getIdentityCard().isBlank()) {
            errors.rejectValue("identityCard", null, "Identity card is required");
        } else if (!accountReq.getIdentityCard().matches(identityRegex)) {
            errors.rejectValue("identityCard", null,"Identity card format is incorrect");
        }

        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (accountReq.getEmail().isBlank()) {
            errors.rejectValue("email", null, "Email is required");
        } else if (!accountReq.getEmail().matches(emailRegex)) {
            errors.rejectValue("email", null,"Email format is incorrect");
        }

        String phoneRegex = "^(0|84)[0-9]{9}$";
        if (accountReq.getPhoneNumber().isBlank()) {
            errors.rejectValue("phoneNumber", null, "Phone number is required");
        } else if (!accountReq.getPhoneNumber().matches(phoneRegex)) {
            errors.rejectValue("phoneNumber", null,"Phone number format is incorrect");
        }

        if (accountReq.getDateOfBirth().isBlank()) {
            errors.rejectValue("dateOfBirth", null, "Date of birth is required");
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(accountReq.getDateOfBirth(), formatter);
                String[] dateReq = accountReq.getDateOfBirth().split("/");
                String[] dateParse = localDate.toString().split("-");
                System.out.println(dateReq[0] +" " + dateReq[1] + " " + dateReq[2]);
                System.out.println(dateParse[0] +" " + dateParse[1] + " " + dateParse[2]);
                if (!dateReq[0].equals(dateParse[2])) {
                    errors.rejectValue("dateOfBirth", null, "Date of birth is incorrect");
                }
             } catch (DateTimeParseException e) {
                errors.rejectValue("dateOfBirth", null,"Date of Birth format is incorrect dd/MM/yyyy");
            }
        }

    }

    public static void main(String[] args) {
        String accountRegex = "^[a-zA-Z0-9]{3,}$";
        String username = "use";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(LocalDate.parse("29/02/2021", formatter));
    }
}
