package com.example.ecommerce.services;

import java.util.List;
import java.util.Optional;

import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.Users;

public interface UserService {
    
    /**
     * Đăng ký user mới
     * @param user thông tin user cần đăng ký
     * @return user đã được tạo
     */
    Users registerUser(Users user);
    
    /**
     * Cập nhật thông tin user
     * @param userId ID của user
     * @param updatedUser thông tin mới
     * @return user đã được cập nhật
     */
    Users updateUser(String userId, Users updatedUser);
    
    /**
     * Lấy danh sách tất cả users
     * @return danh sách users
     */
    List<Users> getAllUsers();
    
    /**
     * Lấy thông tin user theo ID
     * @param userId ID của user
     * @return user
     */
    Users getUserById(String userId);
    
    /**
     * Lấy thông tin user theo username
     * @param username username của user
     * @return user
     */
    Users getUserByUsername(String username);
    
    /**
     * Lấy danh sách địa chỉ của user
     * @param userId ID của user
     * @return danh sách địa chỉ
     */
    List<Address> getUserAddresses(String userId);
    
    /**
     * Xóa user theo username
     * @param username username của user cần xóa
     */
    void deleteUserByUsername(String username);
} 