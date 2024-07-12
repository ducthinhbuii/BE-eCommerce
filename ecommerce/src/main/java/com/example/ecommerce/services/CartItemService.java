package com.example.ecommerce.services;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(String userId, String id, CartItem cartItem);
    public CartItem isCartItemExists(Cart cart, Product product, String size, String userId);
    public void removeCartItem(String cartItemId, String userId);
    public CartItem findCartItemById(String cartItemId);
    
}
