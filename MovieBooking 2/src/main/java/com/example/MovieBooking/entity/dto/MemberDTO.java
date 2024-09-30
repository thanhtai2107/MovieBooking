package com.example.MovieBooking.entity.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    private Long memberId;
    private String memberName;
    private String identityCard;
    private String email;
    private String phoneNumber;
    private String address;
    private String image;
}
