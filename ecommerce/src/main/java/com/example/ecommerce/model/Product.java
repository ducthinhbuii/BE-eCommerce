package com.example.ecommerce.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("product")
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private int price;
    private int discountPrice;
    private int discountPercent;
    private int quantity;
    private LocalDateTime createAt;
    private String brand;
    private String color;
    private String imgUrl;

    @DBRef
    private ArrayList<Rating> listRating;

    @DBRef
    private Set<Size> size = new HashSet<>();

    @DBRef
    private ArrayList<Review> listReview;

    @DBRef
    private Category category;

    // contructor
    public Product(String name, String description, int price, int discountPrice, int discountPercent, int quantity,
            LocalDateTime createAt, String brand, String color, String imgUrl, ArrayList<Rating> listRating,
            Set<Size> size, ArrayList<Review> listReview, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
        this.quantity = quantity;
        this.createAt = createAt;
        this.brand = brand;
        this.color = color;
        this.imgUrl = imgUrl;
        this.listRating = listRating;
        this.size = size;
        this.listReview = listReview;
        this.category = category;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
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

    public ArrayList<Rating> getListRating() {
        return listRating;
    }

    public void setListRating(ArrayList<Rating> listRating) {
        this.listRating = listRating;
    }

    public Set<Size> getSize() {
        return size;
    }

    public void setSize(Set<Size> size) {
        this.size = size;
    }

    public ArrayList<Review> getListReview() {
        return listReview;
    }

    public void setListReview(ArrayList<Review> listReview) {
        this.listReview = listReview;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    

    
    
}
