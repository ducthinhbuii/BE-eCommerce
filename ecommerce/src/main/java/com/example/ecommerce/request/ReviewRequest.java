package com.example.ecommerce.request;

public class ReviewRequest {
    private String productId;
    private String review;
    public ReviewRequest() {
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }

    
}
