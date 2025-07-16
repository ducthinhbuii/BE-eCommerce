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
import com.example.ecommerce.model.Product;
import com.example.ecommerce.request.AddItemRequest;
import com.example.ecommerce.services.CartService;
import com.example.ecommerce.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

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

    @Operation(
        summary = "Get all carts",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(schema = @Schema(implementation = Cart.class))
            )
        }
    )
    @GetMapping("")
    public ResponseEntity<ArrayList<Cart>> getAllCards(){
        return ResponseEntity.ok(cartService.getAllCards());
    }

    @Operation(
        summary = "Get cart by userId",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(schema = @Schema(implementation = Cart.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Cart not found"
            )
        }
    )
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getAllCardss(@PathVariable String userId){
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @Operation(
        summary = "Add item to cart",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Product added",
                content = @Content(schema = @Schema(implementation = Product.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request"
            )
        }
    )
    @PostMapping("/add/{userId}")
    public ResponseEntity<Product> addCart(@PathVariable String userId, @RequestBody AddItemRequest req){
        return ResponseEntity.ok(cartService.addCartItem(userId, req));
    }   

    @Operation(
        summary = "Remove product from cart",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Product removed"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request"
            )
        }
    )
    @PostMapping("/remove/{userId}")
    public ResponseEntity<String> removeCart(@PathVariable String userId, @RequestBody AddItemRequest req){
        return ResponseEntity.ok(cartService.removeProductCart(userId, req));
    }

    @Operation(
        summary = "Remove cart item",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Cart item removed"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request"
            )
        }
    )
    @PostMapping("/remove-cart-item/{userId}")
    public ResponseEntity<String> removeCartItems(@PathVariable String userId, @RequestBody AddItemRequest req){
        return ResponseEntity.ok(cartService.removeCartItem(userId, req));
    }

    @Operation(
        summary = "Reset cart by orderId",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Cart reset successfully"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid orderId"
            )
        }
    )
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
