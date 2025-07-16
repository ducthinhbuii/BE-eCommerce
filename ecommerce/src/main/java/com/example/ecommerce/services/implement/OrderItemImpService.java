package com.example.ecommerce.services.implement;

import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.services.OrderItemService;

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
