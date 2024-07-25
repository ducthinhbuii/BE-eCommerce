package com.example.ecommerce.services;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.request.AddItemRequest;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(String userId, String id, CartItem cartItem);
    public CartItem isCartItemExists(String userId, String productId);
    public String removeCartItem(CartItem cartItem);
    public CartItem findCartItemById(String cartItemId);
    public String removeProductCartItem(CartItem cartItem, AddItemRequest req);
}
