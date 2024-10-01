package com.example.MovieBooking.dto.req;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PromotionDTO {
    private Long promotionId;

    @NotBlank(message = "Detail is required")
    private String detail;

    @Positive(message = "Discount level must be a positive number")
    @NotNull(message = "DiscountLevel is required")
    private BigDecimal discountLevel;

    @NotNull(message = "Start time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    @NotNull(message = "End time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;

    @NotBlank(message = "Title is required")
    private String title;

//    @NotNull(message = "Image is required")
    private MultipartFile image;

    private String imageLink;

//    @Override
//    public String toString() {
//        return "PromotionDTO{" +
//                "promotionId=" + promotionId +
//                ", detail='" + detail + '\'' +
//                ", discountLevel=" + discountLevel +
//                ", startTime=" + startTime +
//                ", endTime=" + endTime +
//                ", title='" + title + '\'' +
//                ", imageLink='" + imageLink + '\'' +
//                '}';
//    }
//    // Getters and Setters
//    public MultipartFile getImage() { return image; }
//    public void setImage(MultipartFile image) { this.image = image; }
}
