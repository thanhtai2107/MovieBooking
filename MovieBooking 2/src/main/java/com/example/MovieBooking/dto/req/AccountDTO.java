package com.example.MovieBooking.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long memberId;
    private String identityCard;
    private String fullname;
    private String phoneNumber;
    private Integer score;


}
