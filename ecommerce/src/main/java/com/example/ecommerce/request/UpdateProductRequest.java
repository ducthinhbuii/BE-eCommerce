package com.example.ecommerce.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private String description;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Integer price;
    
    @NotNull(message = "Discount Price is required")
    private Integer discountPrice;
    
    private Integer discountPercent;
    private Integer quantity;
    private String brand;
    private String color;
    private String imgUrl;
    
    @NotBlank(message = "Category ID is required")
    private String categoryId;
} 