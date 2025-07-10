package com.example.ecommerce.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;

public class AddItemRequest {
    @NotBlank(message = "Product ID is required")
    private String productId;
    
    private String size;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Integer price;
    
    @NotNull(message = "Discount Price is required")
    @Min(value = 0, message = "Discount Price must be non-negative")
    private Integer discountPrice;
    
    public AddItemRequest() {
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getSize() {
        return size;
    }
    public Integer getDiscountPrice() {
        return discountPrice;
    }
    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
}
