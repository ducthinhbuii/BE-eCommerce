package com.example.ecommerce.controller;
import java.time.LocalDateTime;
import java.util.List;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.request.LoginRequest;
import com.example.ecommerce.response.AuthenticationReponse;
import com.example.ecommerce.services.CartService;
import com.example.ecommerce.services.jwt.JWTService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, CartService cartService,
                            AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest user) {
        System.out.println(user);
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(user);
        return ResponseEntity.ok(AuthenticationReponse.builder()
                                    .token(jwt)
                                    .authenticated(true)
                                    .build());
    }

    @PostMapping("/register")
    public ResponseEntity addUser(@RequestBody Users user){
        try {
            if(userRepository.findByUsername(user.getUsername()).isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username has already exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            LocalDateTime now = LocalDateTime.now(); 
            user.setCreateAt(now);
            userRepository.save(user);

            cartService.createCart(user);

            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("")
    public ResponseEntity<List<Users>> getAllUsers(){
        return ResponseEntity.ok(this.userRepository.findAll());
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getUser(@AuthenticationPrincipal UserDetails currentUser) {
        Optional<Users> user = userRepository.findByUsername(currentUser.getUsername());
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity addUser(@PathVariable String userName) {
        Optional<Users> user = userRepository.findByUsername(userName);
        if (user.isPresent()) {
            this.userRepository.deleteByUsername(userName);
            return ResponseEntity.ok("Delete user: " + userName + " Successfull");
        } else {
            return ResponseEntity.ok(userName + " not found");
        }
    }
}
