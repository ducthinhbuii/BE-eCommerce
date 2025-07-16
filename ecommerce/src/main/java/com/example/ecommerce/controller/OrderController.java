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
import com.example.ecommerce.services.implement.OrderImpService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderImpService orderImpService;
    private UserRepository userRepository;

    public OrderController(OrderImpService orderImpService, UserRepository userRepository) {
        this.orderImpService = orderImpService;
        this.userRepository = userRepository;
    }

    @Operation(
        summary = "Get all orders",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of orders",
                content = @Content(schema = @Schema(implementation = Order.class))
            )
        }
    )
    @GetMapping("/")
    public ResponseEntity<ArrayList<Order>>getAllOrders(){
        return ResponseEntity.ok(orderImpService.getAllOrders());
    }

    @Operation(
        summary = "Get user's order history",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "User's order history",
                content = @Content(schema = @Schema(implementation = Order.class))
            )
        }
    )
    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>>userOrderHistory(@PathVariable String userId){
        return ResponseEntity.ok(orderImpService.usersOrderHistory(userId));
    }

    @Operation(
        summary = "Create new order",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Order created",
                content = @Content(schema = @Schema(implementation = Order.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "User not found"
            )
        }
    )
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
