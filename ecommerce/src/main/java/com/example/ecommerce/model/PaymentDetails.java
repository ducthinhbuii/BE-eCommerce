package com.example.ecommerce.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("payment-detail")
public class PaymentDetails {
    @Id
    private String paymentId;
    private String paymentMethod;
    private String paymentStatus;
    private String razorPaymentLinkId;
    private String razorPaymentLinkReferenceId;
    private String razorPaymentLinkStatus;
    private String razorPaymentId;
    
    public PaymentDetails() {
    }
    public String getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public String getRazorPaymentLinkId() {
        return razorPaymentLinkId;
    }
    public void setRazorPaymentLinkId(String razorPaymentLinkId) {
        this.razorPaymentLinkId = razorPaymentLinkId;
    }
    public String getRazorPaymentLinkReferenceId() {
        return razorPaymentLinkReferenceId;
    }
    public void setRazorPaymentLinkReferenceId(String razorPaymentLinkReferenceId) {
        this.razorPaymentLinkReferenceId = razorPaymentLinkReferenceId;
    }
    public String getRazorPaymentLinkStatus() {
        return razorPaymentLinkStatus;
    }
    public void setRazorPaymentLinkStatus(String razorPaymentLinkStatus) {
        this.razorPaymentLinkStatus = razorPaymentLinkStatus;
    }
    public String getRazorPaymentId() {
        return razorPaymentId;
    }
    public void setRazorPaymentId(String razorPaymentId) {
        this.razorPaymentId = razorPaymentId;
    }

    

}
