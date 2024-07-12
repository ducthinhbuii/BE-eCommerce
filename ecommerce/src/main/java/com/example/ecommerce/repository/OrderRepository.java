package com.example.ecommerce.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.example.ecommerce.model.Order;

public interface OrderRepository extends MongoRepository<Order, String>{
    public Order findByOrderId(String orderId);
}
