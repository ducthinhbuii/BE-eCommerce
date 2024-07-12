package com.example.ecommerce.services;

import java.util.ArrayList;

import com.example.ecommerce.model.Rating;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.request.RatingRequest;

public interface RatingService {
    public Rating createRating(RatingRequest req, Users user);
    public ArrayList<Rating> getProductRating(String productId);
}
