package com.example.ecommerce.request;

public class RatingRequest {
    private String productId;
    private double rating;
    
    public RatingRequest() {
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    
}
