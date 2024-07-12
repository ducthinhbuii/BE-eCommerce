package com.example.ecommerce.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cart-item")
public class CartItem {

    @Id
    private String cartItemId;

    private String cartId;
    @DBRef
    private Product product;
    
    private int quantity;
    private String size;
    private int price;
    private int discountPrice;
    private String userId;
    public CartItem() {
    }
    public String getCartItemId() {
        return cartItemId;
    }
    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }
    public String getCart() {
        return cartId;
    }
    public void setCart(String cartId) {
        this.cartId = cartId;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getDiscountPrice() {
        return discountPrice;
    }
    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    
}
