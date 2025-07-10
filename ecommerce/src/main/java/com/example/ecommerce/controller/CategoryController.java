package com.example.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.services.CategoryService;
import com.example.ecommerce.request.CategoryRequest;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/get-all")
    public List<Category> getAllCategory(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/get-top-cat")
    public List<Category> getTopCategory(){
        return categoryService.getTopCategories();
    }

    @GetMapping("/get-by-parent")
    public Category getCategoryByNameAndParent(
        @RequestParam(required = false) String categoryName,
        @RequestParam(required = false) String categoryParentName
        ){
        return categoryService.getCategoryByNameAndParent(categoryName, categoryParentName);
    }

    @GetMapping("/{categoryId}")
    public List<Category> getById(@PathVariable String categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping("/create-category")
    public void createCategory(@RequestBody List<CategoryRequest> categoryRequests){
        categoryService.createCategories(categoryRequests);
    }
}
