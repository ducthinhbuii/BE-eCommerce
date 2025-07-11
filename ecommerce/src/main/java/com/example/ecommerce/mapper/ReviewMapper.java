package com.example.ecommerce.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.ecommerce.dto.ReviewDto;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Review;
import com.example.ecommerce.model.Users;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productId", source = "product.id")
    ReviewDto toDto(Review review);
    
    List<ReviewDto> toDtoList(List<Review> reviews);
    
    @Mapping(target = "user", expression = "java(mapUser(reviewDto.getUserId()))")
    @Mapping(target = "product", expression = "java(mapProduct(reviewDto.getProductId()))")
    Review toEntity(ReviewDto reviewDto);

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