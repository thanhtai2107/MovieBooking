package com.example.MovieBooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Cinema Room Name is required")
    @Column(name = "cinema_name")
    private String cinemaName;

    @NotNull(message = "Seat Quantity is required")
    @Min(value = 1, message = "Seat Quantity must be at least 1")
    @Max(value = 60, message = "Seat Quantity must be less than 60")
    @Column(name = "seat_quantity")
    private Integer seatQuantity;

    @OneToMany(mappedBy = "cinemaRoom")
    private List<Movie> movieList;

    @OneToMany(mappedBy = "cinemaRoom")
    private List<Seat> seatList;
}
