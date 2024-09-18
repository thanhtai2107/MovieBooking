package com.example.MovieBooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SEAT")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @ManyToOne
    @JoinColumn(name = "cinema_room_id")
    private CinemaRoom cinemaRoom;

    @Column(name = "seat_column", columnDefinition = "varchar(255)")
    private String seatColumn;

    @Column(name = "seat_row")
    private Integer seatRow;

    @Column(name = "seat_status")
    private Integer seatStatus;

    @ManyToOne
    @JoinColumn(name = "seat_type_id")
    private SeatType seatType;

    @OneToMany(mappedBy = "seat")
    private List<BookingSeat> bookingSeatList;
}
