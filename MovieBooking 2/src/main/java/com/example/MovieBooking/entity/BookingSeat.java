package com.example.MovieBooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKING_SEAT")
public class BookingSeat {
    @Id
    @GeneratedValue
    @Column(name = "booking_seat_id")
    private Long bookingSeatId;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
