package com.example.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ecommerce.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String>{
    Category findByName(String category);

    @Query("{name: ?0, parentCategoryName: ?1}")
    Category findByNameAndParent(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);
}
