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
@Table(name = "SEAT_TYPE")
public class SeatType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seatTypeId;

    @Column(name = "seat_type_name")
    private String seatTypeName;

    private Long price;

    @OneToMany(mappedBy = "seatType")
    private List<Seat> seatList;
}
