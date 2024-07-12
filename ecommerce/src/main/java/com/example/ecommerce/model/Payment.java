package com.example.ecommerce.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("payment")
public class Payment {
    @Id
    private String paymentId;
    private String cardNumber;
    private LocalDate localDate;
    private String cvv;
}
