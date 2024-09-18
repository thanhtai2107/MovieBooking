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
@Table(name = "CINEMA_ROOM")
public class CinemaRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinema_room_id")
    private Long cinemaRoomId;

    @Column(name = "cinema_name")
    private String cinemaName;

    @Column(name = "seat_quantity")
    private Integer seatQuantity;

    @OneToMany(mappedBy = "cinemaRoom")
    private List<Movie> movieList;

    @OneToMany(mappedBy = "cinemaRoom")
    private List<Seat> seatList;
}
