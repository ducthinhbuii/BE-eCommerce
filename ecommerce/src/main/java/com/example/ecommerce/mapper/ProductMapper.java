package com.example.ecommerce.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.model.Product;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, SizeMapper.class, RatingMapper.class, ReviewMapper.class})
public interface ProductMapper {
    
    @Mapping(target = "averageRating", source = "listRating", qualifiedByName = "calculateAverageRating")
    @Mapping(target = "totalReviews", source = "listReview", qualifiedByName = "calculateTotalReviews")
    @Mapping(target = "inStock", source = "quantity", qualifiedByName = "checkInStock")
    @Mapping(target = "sizes", source = "size")
    @Mapping(target = "ratings", source = "listRating")
    @Mapping(target = "reviews", source = "listReview")
    ProductDto toDto(Product product);
    
    List<ProductDto> toDtoList(List<Product> products);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "listRating", ignore = true)
    @Mapping(target = "listReview", ignore = true)
    @Mapping(target = "size", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDto productDto);
    
    @Named("calculateAverageRating")
    default Double calculateAverageRating(List<com.example.ecommerce.model.Rating> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }
        return ratings.stream()
                .mapToDouble(rating -> rating.getRating())
                .average()
                .orElse(0.0);
    }
    
    @Named("calculateTotalReviews")
    default Integer calculateTotalReviews(List<com.example.ecommerce.model.Review> reviews) {
        return reviews != null ? reviews.size() : 0;
    }
    
    @Named("checkInStock")
    default Boolean checkInStock(Integer quantity) {
        return quantity != null && quantity > 0;
    }
} 