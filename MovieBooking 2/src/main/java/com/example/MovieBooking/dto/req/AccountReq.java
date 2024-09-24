package com.example.MovieBooking.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AccountReq {
    private Long id;
    private String username;
    private String password;
    private String confirmPassword;
    @NotBlank(message = "Address is required")
    private String address;
//    @DateTimeFormat(pattern = "dd/MM/yyyy", iso = DateTimeFormat.ISO.DATE)
    private String dateOfBirth;
    private String email;
    @NotBlank(message = "Full name is required")
    private String fullname;
    @NotEmpty(message = "Gender is required")
    private String gender;
    private String identityCard;
    private String image;
    private String phoneNumber;
}
