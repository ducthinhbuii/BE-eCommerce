package com.example.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.services.OrderImpService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderImpService orderImpService;
    private UserRepository userRepository;

    public OrderController(OrderImpService orderImpService, UserRepository userRepository) {
        this.orderImpService = orderImpService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ResponseEntity<ArrayList<Order>>getAllOrders(){
        return ResponseEntity.ok(orderImpService.getAllOrders());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>>userOrderHistory(@PathVariable String userId){
        return ResponseEntity.ok(orderImpService.usersOrderHistory(userId));
    }

    @PostMapping("/create-new/{userId}")
    public ResponseEntity<Order>createOrder(@PathVariable("userId") String userId, @RequestBody Address shippingAddress){
        Optional<Users> user = userRepository.findById(userId);
        if(user.isPresent()){
            Order order = orderImpService.createOrder(user.get(), shippingAddress);
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
