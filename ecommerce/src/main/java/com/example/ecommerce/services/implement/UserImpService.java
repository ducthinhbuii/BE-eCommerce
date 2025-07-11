package com.example.ecommerce.services.implement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.AddressDto;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.exception.ConflictException;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.mapper.AddressMapper;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.services.CartService;
import com.example.ecommerce.services.UserService;

@Service
public class UserImpService implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    
    public UserImpService(UserRepository userRepository, PasswordEncoder passwordEncoder, CartService cartService,
                            UserMapper userMapper, AddressMapper addressMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
    }
    
    @Override
    public Users registerUser(Users user) {
        // Business validation: Kiểm tra username đã tồn tại chưa
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ConflictException("Username has already exists");
        }
        
        // Business logic: Encode password và set create time
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateAt(LocalDateTime.now());
        
        // Save user
        Users savedUser = userRepository.save(user);
        
        // Business logic: Tạo cart cho user mới
        cartService.createCart(savedUser);
        
        return savedUser;
    }
    
    @Override
    public Users updateUser(String userId, Users updatedUser) {
        Users existingUser = getUserById(userId); // Sẽ throw exception nếu không tìm thấy
        
        // Update fields
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAvatar(updatedUser.getAvatar());
        
        return userRepository.save(existingUser);
    }
    
    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }
    
    @Override
    public Users getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }
    
    @Override
    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }
    
    @Override
    public List<AddressDto> getUserAddresses(String userId) {
        Users user = getUserById(userId); // Sẽ throw exception nếu không tìm thấy
        return addressMapper.toDtoList(user.getListAddress());
    }
    
    @Override
    public void deleteUserByUsername(String username) {
        if (!userRepository.findByUsername(username).isPresent()) {
            throw new ResourceNotFoundException("User", "username", username);
        }
        userRepository.deleteByUsername(username);
    }
} 