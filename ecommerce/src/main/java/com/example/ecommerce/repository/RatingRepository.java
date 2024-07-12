package com.example.ecommerce.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ecommerce.model.Rating;

public interface RatingRepository extends MongoRepository<Rating, String> {

    @Query("{product.id: ?0}")
    public ArrayList<Rating> getAllProductRating(@Param("productId") String productId);
}
