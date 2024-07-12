package com.example.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ecommerce.model.OrderItem;

public interface OrderItemRepository extends MongoRepository<OrderItem, String>{
    
}
