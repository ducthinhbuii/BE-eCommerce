package com.example.ecommerce.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInfoResponse implements Serializable {
    private String status;
    private String message;
    private String data;
}
