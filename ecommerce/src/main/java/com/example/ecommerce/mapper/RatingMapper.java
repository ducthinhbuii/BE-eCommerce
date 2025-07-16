package com.example.ecommerce.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.ecommerce.dto.RatingDto;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Rating;
import com.example.ecommerce.model.Users;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productId", source = "product.id")
    RatingDto toDto(Rating rating);
    
    List<RatingDto> toDtoList(List<Rating> ratings);
    
    @Mapping(target = "user", expression = "java(mapUser(ratingDto.getUserId()))")
    @Mapping(target = "product", expression = "java(mapProduct(ratingDto.getProductId()))")
    Rating toEntity(RatingDto ratingDto);

    // Default mapping methods
    default Users mapUser(String userId) {
        if (userId == null) return null;
        Users user = new Users();
        user.setId(userId);
        return user;
    }

    default Product mapProduct(String productId) {
        if (productId == null) return null;
        Product product = new Product();
        product.setId(productId);
        return product;
    }
} 