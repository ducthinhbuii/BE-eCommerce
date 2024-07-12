package com.example.ecommerce.repository;
// import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.data.mongodb.repository.Query;
// import org.springframework.data.repository.query.Param;

import com.example.ecommerce.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
    Optional<Product> findByCategory(String category);

    // @Query("{$and: [" +
    //     "{$or: [{category.name: ?0}, {category.name: ''}]}" +
    //     "{$or: [{$and: [{?1: ''}, {?2: ''}]}, {discountPrice: {$gte: ?1, $lte: ?2}}]}" +
    //     "{$or: [{?3: ''}, {discountPercent: {$gte: ?3}}]}" +
    //     "{$or: [{price: {$gte: ?1, $lte: ?2}}]}" +
    // "]}")

    // public ArrayList<Product> fillteProducts(
    //     @Param("category") String category,
    //     @Param("minPrice") int minPrice,
    //     @Param("maxPrice") int maxPrice,
    //     @Param("minDiscount") int minDiscount,
    //     @Param("sort") String sort
    // );
}
