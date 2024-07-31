package com.example.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ecommerce.model.Cart;

public interface CartRepository extends MongoRepository<Cart, String> {

    @Query("{'users.id': ?0}")
    Cart findByUserId(String userId);
    Cart findByCartId(String cartId);
}
