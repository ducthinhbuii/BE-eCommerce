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

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

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

        String jwt = jwtService.generateTokenLogin(user.getUsername());
        return ResponseEntity.ok(AuthenticationReponse.builder()
                                    .token(jwt)
                                    .authenticated(true)
                                    .build());
    }

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

    @GetMapping("/access-token")
    public String home(@AuthenticationPrincipal OidcUser principal) {
        OAuth2AuthorizedClient authorizedClient = authorizedClientService
                .loadAuthorizedClient(
                        "google",
                        principal.getName());

        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        return accessToken;
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody Users user){
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

    @PostMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody Users newUser){
        Optional<Users> user = userRepository.findById(userId);
        if(user.isPresent()){
            Users currentUser = user.get();
            currentUser.setFirstName(newUser.getFirstName());
            currentUser.setLastName(newUser.getLastName());
            currentUser.setEmail(newUser.getEmail());
            currentUser.setAvatar(newUser.getAvatar());
            userRepository.save(currentUser);
            return ResponseEntity.ok("update user successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Users>> getAllUsers(){
        return ResponseEntity.ok(this.userRepository.findAll());
    }

    @GetMapping("/address/{userId}")
    public ResponseEntity<List<Address>> getAddressUser(@PathVariable String userId) {
        Optional<Users> user = userRepository.findById(userId);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get().getListAddress());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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

    @GetMapping("/google/me")
    public ResponseEntity<Object> getUser(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser != null) {
            System.out.println(oidcUser.getEmail());
            Optional<Users> user = userRepository.findByUsername(oidcUser.getEmail());
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    
    @GetMapping("/gg-me")
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes(); // Google user info
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
