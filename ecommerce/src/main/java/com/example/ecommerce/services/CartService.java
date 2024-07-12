package com.example.ecommerce.services;

import java.util.ArrayList;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.request.AddItemRequest;

public interface CartService {
    public Cart createCart(Users user);
    public String addCartItem(String userId, AddItemRequest req);
    public Cart findUserCart(String userId);
    public ArrayList<Cart> getAllCards();
}
