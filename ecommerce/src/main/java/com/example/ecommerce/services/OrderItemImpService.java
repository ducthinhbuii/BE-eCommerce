package com.example.ecommerce.services;

import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.repository.OrderItemRepository;

public class OrderItemImpService implements OrderItemService {

    private OrderItemRepository orderItemRepository;
    public OrderItemImpService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
    
}
