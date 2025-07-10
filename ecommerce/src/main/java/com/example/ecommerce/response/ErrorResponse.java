package com.example.ecommerce.response;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;
    private Map<String, String> errors;
    private List<String> detail;
    
    // Constructor cho backward compatibility
    public ErrorResponse(String error, List<String> detail) {
        this.error = error;
        this.detail = detail;
    }
    
    // Trường cũ để backward compatibility
    private String error;
}
