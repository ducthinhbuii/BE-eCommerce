package com.example.ecommerce.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.request.AddItemRequest;
import com.example.ecommerce.services.CartService;
import com.example.ecommerce.services.OrderService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
// Suggested code may be subject to a license. Learn more: ~LicenseLog:551062463.
    private final CartService cartService;
    private final OrderService orderService;

    public CartController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping("")
    public ResponseEntity<ArrayList<Cart>> getAllCards(){
        return ResponseEntity.ok(cartService.getAllCards());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getAllCardss(@PathVariable String userId){
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<String> addCart(@PathVariable String userId, @RequestBody AddItemRequest req){
        return ResponseEntity.ok(cartService.addCartItem(userId, req));
    }

    @PostMapping("/remove/{userId}")
    public ResponseEntity<String> removeCart(@PathVariable String userId, @RequestBody AddItemRequest req){
        return ResponseEntity.ok(cartService.removeProductCart(userId, req));
    }

    @PostMapping("/remove-cart-item/{userId}")
    public ResponseEntity<String> removeCartItems(@PathVariable String userId, @RequestBody AddItemRequest req){
        return ResponseEntity.ok(cartService.removeCartItem(userId, req));
    }

    @GetMapping("/reset-cart/{orderId}")
    public ResponseEntity<?> resetCard(@PathVariable String orderId){
        try {
            Order order = orderService.findOrderById(orderId);
            System.out.println(order.getCart().getCartId());
            cartService.resetCart(order.getCart().getCartId());
            return ResponseEntity.ok("remove success");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
        
    }
}
