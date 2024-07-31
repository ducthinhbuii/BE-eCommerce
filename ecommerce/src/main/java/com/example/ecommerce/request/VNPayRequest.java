package com.example.ecommerce.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VNPayRequest implements Serializable {
    private String orderId;
    private long totalPrice;
}
