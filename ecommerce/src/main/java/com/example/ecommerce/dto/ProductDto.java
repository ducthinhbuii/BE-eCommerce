package com.example.ecommerce.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String id;
    private String name;
    private String description;
    private Integer price;
    private Integer discountPrice;
    private Integer discountPercent;
    private Integer quantity;
    private String brand;
    private String color;
    private String imgUrl;
    private LocalDateTime createAt;
    private CategoryDto category;
    private Set<SizeDto> sizes;
    private List<RatingDto> ratings;
    private List<ReviewDto> reviews;
    
    // Calculated fields
    private Double averageRating;
    private Integer totalReviews;
    private Boolean inStock;
} 