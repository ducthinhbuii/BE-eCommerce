package com.example.ecommerce.request;

import java.util.HashSet;
import java.util.Set;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Size;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public class CreateProductRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Integer price;

    @NotNull(message = "Discount Price is required")
    @Min(value = 0, message = "Discount Price must be non-negative")
    private Integer discountPrice;
    
    @Min(value = 0, message = "Discount Percent must be non-negative")
    private Integer discountPercent;
    
    @Min(value = 0, message = "Quantity must be non-negative")
    private Integer quantity;
    
    private String brand;
    private String color;
    private String imgUrl;

    @NotBlank(message = "Category is required")
    private String categoryId;

    private Set<Size> size = new HashSet<>();
    private String topLeverCategory;
    private String secondLeverCategory;
    private String thirdLeverCategory;

    public CreateProductRequest() {
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Integer getDiscountPrice() {
        return discountPrice;
    }
    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }
    public Integer getDiscountPercent() {
        return discountPercent;
    }
    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getCategoryId() {
        return categoryId;
    }
    public void setCategory(String category) {
        this.categoryId = category;
    }
    public Set<Size> getSize() {
        return size;
    }
    public void setSize(Set<Size> size) {
        this.size = size;
    }
    public String getTopLeverCategory() {
        return topLeverCategory;
    }
    public void setTopLeverCategory(String topLeverCategory) {
        this.topLeverCategory = topLeverCategory;
    }
    public String getSecondLeverCategory() {
        return secondLeverCategory;
    }
    public void setSecondLeverCategory(String secondLeverCategory) {
        this.secondLeverCategory = secondLeverCategory;
    }
    public String getThirdLeverCategory() {
        return thirdLeverCategory;
    }
    public void setThirdLeverCategory(String thirdLeverCategory) {
        this.thirdLeverCategory = thirdLeverCategory;
    }
}
