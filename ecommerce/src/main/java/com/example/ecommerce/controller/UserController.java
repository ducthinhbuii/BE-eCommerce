package com.example.ecommerce.controller;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.request.LoginRequest;
import com.example.ecommerce.response.AuthenticationReponse;
import com.example.ecommerce.services.UserService;
import com.example.ecommerce.services.jwt.JWTService;
import com.example.ecommerce.dto.AddressDto;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.exception.ResourceNotFoundException;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Operation(
        summary = "User login",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Login successful",
                content = @Content(schema = @Schema(implementation = AuthenticationReponse.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid credentials"
            )
        }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest user) {
        System.out.println(user);
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(user.getUsername());
        return ResponseEntity.ok(AuthenticationReponse.builder()
                                    .token(jwt)
                                    .authenticated(true)
                                    .build());
    }

    @Operation(
        summary = "Google OAuth2 login",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Google login successful"
            )
        }
    )
    @GetMapping("/login-google")
    public Map<String, Object> loginGoogle(OAuth2AuthenticationToken authentication) {
        System.out.println("Login google");
        if (authentication == null) {
            throw new RuntimeException("OAuth2AuthenticationToken is null");
        }

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        if (oauth2User == null) {
            throw new RuntimeException("OAuth2User is null");
        }
        // System.out.println(oauth2User.getAuthorities());
        return oauth2User.getAttributes();
    }

    @Operation(
        summary = "Get Google OAuth2 access token",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Returns Google access token"
            )
        }
    )
    @GetMapping("/access-token")
    public String home(@AuthenticationPrincipal OidcUser principal) {
        OAuth2AuthorizedClient authorizedClient = authorizedClientService
                .loadAuthorizedClient(
                        "google",
                        principal.getName());

        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        return accessToken;
    }

    @Operation(
        summary = "Register new user",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "User registered successfully"
            )
        }
    )
    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody Users user){
        Users registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @Operation(
        summary = "Update user info",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "User updated successfully"
            )
        }
    )
    @PostMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody Users newUser){
        Users updatedUser = userService.updateUser(userId, newUser);
        return ResponseEntity.ok("update user successfully");
    }

    @Operation(
        summary = "Get all users",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of users",
                content = @Content(schema = @Schema(implementation = UserDto.class))
            )
        }
    )
    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
        summary = "Get user addresses",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of addresses",
                content = @Content(schema = @Schema(implementation = AddressDto.class))
            )
        }
    )
    @GetMapping("/address/{userId}")
    public ResponseEntity<List<AddressDto>> getAddressUser(@PathVariable String userId) {
        List<AddressDto> addresses = userService.getUserAddresses(userId);
        return ResponseEntity.ok(addresses);
    }

    @Operation(
        summary = "Get current user info",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "User info",
                content = @Content(schema = @Schema(implementation = Users.class))
            )
        }
    )
    @GetMapping("/me")
    public ResponseEntity<Object> getUser(@AuthenticationPrincipal UserDetails currentUser) {
        Users user = userService.getUserByUsername(currentUser.getUsername());
        return ResponseEntity.ok(user);
    }

    @Operation(
        summary = "Get Google user info (OIDC)",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "User info",
                content = @Content(schema = @Schema(implementation = Users.class))
            )
        }
    )
    @GetMapping("/google/me")
    public ResponseEntity<Object> getUser(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser != null) {
            System.out.println(oidcUser.getEmail());
            Users user = userService.getUserByUsername(oidcUser.getEmail());
            return ResponseEntity.ok(user);
        }
        throw new ResourceNotFoundException("User", "OAuth2", "not found");
    }
    
    @Operation(
        summary = "Get Google user info (OAuth2)",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Google user info"
            )
        }
    )
    @GetMapping("/gg-me")
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes(); // Google user info
    }

    @Operation(
        summary = "Delete user by username",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "User deleted successfully"
            )
        }
    )
    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName) {
        userService.deleteUserByUsername(userName);
        return ResponseEntity.ok("Delete user: " + userName + " Successfully");
    }
}
