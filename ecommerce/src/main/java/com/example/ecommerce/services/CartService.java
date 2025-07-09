package com.example.ecommerce.services;

import java.util.ArrayList;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.request.AddItemRequest;

public interface CartService {
    public Cart createCart(Users user);
    public Product addCartItem(String userId, AddItemRequest req);
    public Cart findUserCart(String userId);
    public ArrayList<Cart> getAllCards();
    public Cart getCartByUserId(String userId);
    public String removeProductCart(String userId, AddItemRequest req);
    public String removeCartItem(String userId, AddItemRequest req);
    public String resetCart(String cartId);
}
