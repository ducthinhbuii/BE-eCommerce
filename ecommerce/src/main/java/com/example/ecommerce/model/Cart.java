package com.example.ecommerce.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cart")
public class Cart {
    @Id
    public String cartId;

    @DBRef
    private Users users;
    @DBRef
    private Set<CartItem> cartItems = new HashSet<>();

    private double totalPrice;
    private int totalDiscountPrice;
    private int totalItem;
    private int discount;
    public Cart() {
    }
    
    public String getCartId() {
        return cartId;
    }
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
    public Users getUsers() {
        return users;
    }
    public void setUsers(Users users) {
        this.users = users;
    }
    public Set<CartItem> getCartItems() {
        return cartItems;
    }
    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public int getTotalDiscountPrice() {
        return totalDiscountPrice;
    }
    public void setTotalDiscountPrice(int totalDiscountPrice) {
        this.totalDiscountPrice = totalDiscountPrice;
    }
    public int getDiscount() {
        return discount;
    }
    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }
    

}
