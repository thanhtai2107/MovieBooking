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
@Table(name = "SCHEDULE")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(name = "schedule_time", columnDefinition = "varchar(255)")
    private String scheduleTime;

    @OneToMany(mappedBy = "schedule")
    private List<MovieSchedule> movieScheduleList;

    @OneToMany(mappedBy = "schedule")
    private List<BookingSeat> bookingSeatList;
}
