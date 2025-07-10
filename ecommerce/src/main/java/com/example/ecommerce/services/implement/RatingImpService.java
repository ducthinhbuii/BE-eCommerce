package com.example.ecommerce.services.implement;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.example.ecommerce.services.ProductService;
import com.example.ecommerce.services.RatingService;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Rating;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.RatingRepository;
import com.example.ecommerce.request.RatingRequest;

@Service
public class RatingImpService implements RatingService {

    private RatingRepository ratingRepository;
    private ProductService productService;
    
    public RatingImpService(RatingRepository ratingRepository, ProductService productService) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
    }

    @Override
    public Rating createRating(RatingRequest req, Users user) {
        Product product = productService.findById(req.getProductId());
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setRating(req.getRating());
        rating.setUser(user);
        rating.setCreateAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public ArrayList<Rating> getProductRating(String productId) {
       return ratingRepository.getAllProductRating(productId);
    }
    
}
