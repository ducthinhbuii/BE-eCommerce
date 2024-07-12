package com.example.ecommerce.services;

import java.util.ArrayList;
import java.util.List;

import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Users;

public interface OrderService {
    public Order createOrder(Users user, Address shippingAddress);
    public Order findOrderById(String orderId);
    public List<Order> usersOrderHistory(String userId);
    public Order placedOrder(String orderId);
    public Order confirmOrder(String orderId);
    public Order shippedOrder(String orderId);
    public Order deliveredOrder(String orderId);
    public Order canceledOrder(String orderId);
    public ArrayList<Order> getAllOrders();
    public void deleteOrder(String orderId);
    public List<Order> getUsersOrder(String userId);

}
