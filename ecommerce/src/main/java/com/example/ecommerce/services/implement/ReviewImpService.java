package com.example.ecommerce.services.implement;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.example.ecommerce.services.ProductService;
import com.example.ecommerce.services.ReviewService;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Review;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.ReviewRepository;
import com.example.ecommerce.request.ReviewRequest;

@Service
public class ReviewImpService implements ReviewService {

    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ProductRepository productRepository;

    public ReviewImpService(ReviewRepository reviewRepository, ProductService productService,
            ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public Review createReview(ReviewRequest req, Users user) {
        Product product = productService.findById(req.getProductId());
        Review review = new Review();
        review.setProduct(product);
        review.setReviewText(req.getReview());
        review.setUser(user);
        review.setCreateAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Override
    public ArrayList<Review> getAllReview(String productId) {
        return reviewRepository.getAllProductReview(productId);
    }
    
}
