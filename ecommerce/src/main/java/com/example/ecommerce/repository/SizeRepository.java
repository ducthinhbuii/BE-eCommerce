package com.example.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ecommerce.model.Size;

public interface SizeRepository extends MongoRepository<Size, String> {

}
