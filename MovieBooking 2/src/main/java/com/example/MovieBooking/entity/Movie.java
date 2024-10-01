package com.example.MovieBooking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<MovieDate> movieDateList;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonBackReference
    private List<MovieSchedule> movieScheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonBackReference
    private List<MovieType> movieTypeList = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonBackReference
    private List<Booking> bookingList;
  
    @OneToMany(mappedBy = "movie")
    private List<BookingSeat> bookingSeatList;

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", actor='" + actor + '\'' +
                ", cinemaRoom=" + cinemaRoom +
                ", content='" + content + '\'' +
                ", director='" + director + '\'' +
                ", duration=" + duration +
                ", fromDate=" + fromDate +
                ", productionCompany='" + productionCompany + '\'' +
                ", toDate=" + toDate +
                ", version='" + version + '\'' +
                ", nameEnglish='" + nameEnglish + '\'' +
                ", nameVN='" + nameVN + '\'' +
                ", largeImage='" + largeImage + '\'' +
                ", smallImage='" + smallImage + '\'' +
                ", movieDateList=" + movieDateList +
                ", movieScheduleList=" + movieScheduleList +
                ", movieTypeList=" + movieTypeList +
                ", bookingList=" + bookingList +
                '}';
    }
    

}
