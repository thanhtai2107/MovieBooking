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
@Table(name = "MOVIE")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(columnDefinition = "nvarchar(255)")
    private String actor;

    @ManyToOne
    @JoinColumn(name = "cinema_room_id")
    private CinemaRoom cinemaRoom;

    @Column(columnDefinition = "nvarchar(1000)")
    private String content;

    @Column(columnDefinition = "nvarchar(255)")
    private String director;

    private Integer duration;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "production_company", columnDefinition = "nvarchar(255)")
    private String productionCompany;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(columnDefinition = "nvarchar(255)")
    private String version;

    @Column(name = "name_english", columnDefinition = "varchar(255)")
    private String nameEnglish;

    @Column(name = "name_vn", columnDefinition = "nvarchar(255)")
    private String nameVN;

    @Column(name = "large_image", columnDefinition = "varchar(255)")
    private String largeImage;

    @Column(name = "small_image", columnDefinition = "varchar(255)")
    private String smallImage;

    @OneToMany(mappedBy = "movie")
    private List<MovieDate> movieDateList;

    @OneToMany(mappedBy = "movie")
    private List<MovieSchedule> movieScheduleList;

    @OneToMany(mappedBy = "movie")
    private List<MovieType> movieTypeList;

    @OneToMany(mappedBy = "movie")
    private List<Booking> bookingList;
}
