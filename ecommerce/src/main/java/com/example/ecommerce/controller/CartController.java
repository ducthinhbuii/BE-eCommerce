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
import com.example.ecommerce.request.AddItemRequest;
import com.example.ecommerce.services.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
// Suggested code may be subject to a license. Learn more: ~LicenseLog:551062463.
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("")
    public ResponseEntity<ArrayList<Cart>> getAllCards(){
        return ResponseEntity.ok(cartService.getAllCards());
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<String> addCart(@PathVariable String userId, @RequestBody AddItemRequest req){
        return ResponseEntity.ok(cartService.addCartItem(userId, req));
    }
}
