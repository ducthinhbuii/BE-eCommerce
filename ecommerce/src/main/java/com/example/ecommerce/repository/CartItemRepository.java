package com.example.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;

public interface CartItemRepository extends MongoRepository<CartItem, String> {

    // @Query("{cart: ?0, product: ?1, size: ?2, userId: ?3}")
    // public CartItem isCartItemExists(@Param("cart") Cart cart, 
    //                                 @Param("product") Product product, 
    //                                 @Param("size") String size, 
    //                                 @Param("userId") String userId);
}
