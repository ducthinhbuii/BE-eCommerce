package com.example.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ecommerce.model.PaymentDetails;

public interface PaymentDetailRepository extends MongoRepository<PaymentDetails, String> {
    public PaymentDetails findByPaymentId(String orderId);
}
