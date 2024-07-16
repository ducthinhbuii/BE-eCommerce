package com.example.ecommerce.request;

import java.util.HashSet;
import java.util.Set;


import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Size;

public class CreateProductRequest {
    private String name;
    private String description;
    private int price;
    private int discountPrice;
    private int discountPercent;
    private int quantity;
    private String brand;
    private String color;
    private String imgUrl;
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
    public int getDiscountPercent() {
        return discountPercent;
    }
    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
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
