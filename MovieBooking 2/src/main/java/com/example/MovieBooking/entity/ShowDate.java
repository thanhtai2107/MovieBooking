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
@Table(name = "SHOW_DATE")
public class ShowDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_date_id")
    private Long showDateId;

    @Column(name = "show_date")
    private LocalDate showDate;

    @Column(name = "date_name", columnDefinition = "nvarchar(255)")
    private String dateName;

    @OneToMany(mappedBy = "showDate")
    private List<MovieDate> movieDateList;
}
