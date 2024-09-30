package com.example.MovieBooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

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
//    @NotBlank(message = "Detail is required")
    private String detail;

    @Column(name = "discount_level")
//    @Positive(message = "Discount level must be a positive number")
    private BigDecimal discountLevel;

    @Column(name = "start_time")
//    @NotBlank(message = "Start time is required")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Please enter a valid date!")
    private LocalDate startTime;

    @Column(name = "end_time")
//    @NotBlank(message = "End time is required")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Please enter a valid date!")
    private LocalDate endTime;

    @Column(columnDefinition = "varchar(255)")
//    @NotBlank(message = "Image is required")
//    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif))$)", message = "Please upload a valid image file")
    private String image;

    @Column(columnDefinition = "nvarchar(255)")
//    @NotBlank(message = "Title is required")
//    @Size(min = 1, max = 100, message = "Title max length is 28 characters")
    private String title;

}
