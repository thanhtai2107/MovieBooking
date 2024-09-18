package com.example.MovieBooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROMOTION")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionId;

    @Column(columnDefinition = "nvarchar(255)")
    private String detail;

    @Column(name = "discount_level")
    private Integer discountLevel;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(columnDefinition = "varchar(255)")
    private String image;

    @Column(columnDefinition = "nvarchar(255)")
    private String title;

}
