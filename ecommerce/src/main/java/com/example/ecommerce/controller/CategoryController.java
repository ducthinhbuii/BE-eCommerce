package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.request.CategoryRequest;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    public CategoryController(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/create-category")
    public void createCategory(@RequestBody List<CategoryRequest> listReq){
        for(CategoryRequest categoryRequest : listReq){
            saveCategoryRecursive(categoryRequest, null, 1);
        }
    }

    private void saveCategoryRecursive(CategoryRequest categoryRequest, Category parent, int level) {
        if(categoryRepository.findByName(categoryRequest.getName()) != null){
            return;
        }

        Category newCategory = new Category();
        newCategory.setName(categoryRequest.getName());
        newCategory.setLevel(level);
        newCategory.setCategoryParent(parent);

        Category savedCategory = categoryRepository.save(newCategory);
        
        if(categoryRequest.getChildren() != null){
            for(CategoryRequest child : categoryRequest.getChildren()){
                saveCategoryRecursive(child, savedCategory, level + 1);
            }
        }
        return;
    }
}
