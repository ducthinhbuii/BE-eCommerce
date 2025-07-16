package com.example.ecommerce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String categoryId;
    private String name;
    private String categoryParentId;
    private String categoryParentName;
    private Integer level;
    private List<CategoryDto> children;
} 