package com.example.MovieBooking.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private long bookingId;
    private long memberId;
    private String fullName;
    private String identityCard;
    private String phoneNumber;
    private String movie;
    private LocalDate date;
    private String time;
    private String seat;
    private int status;
    private String screen;
    private long total;
    private Long useScore;


    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingId=" + bookingId +
                ", memberId=" + memberId +
                ", fullName='" + fullName + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", movie='" + movie + '\'' +
                ", date=" + date +
                ", time='" + time + '\'' +
                ", seat='" + seat + '\'' +
                ", status=" + status +
                ", screen='" + screen + '\'' +
                ", total=" + total +
                ", useScore=" + useScore +
                '}';
    }
}
