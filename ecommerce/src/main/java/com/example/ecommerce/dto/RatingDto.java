package com.example.ecommerce.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    private String ratingId;
    private Double rating;
    private String userId;
    private String productId;
    private LocalDateTime createAt;
} 