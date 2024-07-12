package com.example.ecommerce.services;

import java.util.ArrayList;

import com.example.ecommerce.model.Review;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.request.ReviewRequest;

public interface ReviewService {
    public Review createReview(ReviewRequest req, Users user);
    public ArrayList<Review> getAllReview(String productId);
}
