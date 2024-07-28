package com.example.ecommerce.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.Users;

public interface UserRepository extends MongoRepository<Users, String> {
    Optional<Users> findByUsername(String username);
    Optional<Users> deleteByUsername(String username);
}
