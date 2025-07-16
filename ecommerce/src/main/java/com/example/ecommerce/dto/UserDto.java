package com.example.ecommerce.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String role;
    private Boolean isOauth;
    private String avatar;
    private LocalDateTime createAt;
    private List<AddressDto> listAddress;
    
    // Không bao gồm password và sensitive data
} 