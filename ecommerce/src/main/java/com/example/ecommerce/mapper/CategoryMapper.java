package com.example.ecommerce.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.ecommerce.dto.CategoryDto;
import com.example.ecommerce.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    
    CategoryDto toDto(Category category);
    
    List<CategoryDto> toDtoList(List<Category> categories);
    
    Category toEntity(CategoryDto categoryDto);

    List<Category> toEntityList(List<CategoryDto> categoryDtos);
} 