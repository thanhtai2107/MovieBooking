package com.example.MovieBooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKING")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "show_date_id")
    private ShowDate showDate;

    @Column(name = "add_score")
    private Long addScore;

    @Column(name = "use_score")
    private Long useScore;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "totalMoney")
    private Long totalMoney;

    private Integer status;

    @OneToMany(mappedBy = "booking")
    private List<BookingSeat> bookingSeatList;
}
