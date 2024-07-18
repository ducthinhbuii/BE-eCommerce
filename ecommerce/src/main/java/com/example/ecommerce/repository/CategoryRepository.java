package com.example.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ecommerce.model.Category;


public interface CategoryRepository extends MongoRepository<Category, String>{
    Category findByName(String category);
    List<Category> findByCategoryId(String categoryId);

    @Query("{name: ?0, 'categoryParent.name': ?1}")
    Category findByNameAndParent(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);
}
