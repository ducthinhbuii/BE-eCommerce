package com.example.ecommerce.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ecommerce.model.Review;

public interface ReviewRepository extends MongoRepository<Review, String> {
    @Query("{product.id: ?0}")
    public ArrayList<Review> getAllProductReview(@Param("productId") String productId);
}
