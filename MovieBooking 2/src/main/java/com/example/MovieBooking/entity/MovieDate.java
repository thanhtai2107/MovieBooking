package com.example.MovieBooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MOVIE_DATE")
@IdClass(MovieDate.class)
public class MovieDate implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Id
    @ManyToOne
    @JoinColumn(name = "show_date_id")
    private ShowDate showDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDate movieDate = (MovieDate) o;
        return Objects.equals(movie, movieDate.movie) &&
               Objects.equals(showDate, movieDate.showDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, showDate);
    }
}
