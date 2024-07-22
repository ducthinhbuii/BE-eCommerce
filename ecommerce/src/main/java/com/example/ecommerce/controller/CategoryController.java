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
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.request.CategoryRequest;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    public CategoryController(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/get-all")
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    @GetMapping("/get-top-cat")
    public List<Category> getTopCategory(){
        return categoryRepository.findTopCategory();
    }

    @GetMapping("/get-by-parent")
    public Category getCategoryByNameAndParent(
        @RequestParam(required = false) String categoryName,
        @RequestParam(required = false) String categoryParentName
        ){
        return categoryRepository.findByNameAndParent(categoryName, categoryParentName);
    }

    @GetMapping("/{categoryId}")
    public List<Category> getById(@PathVariable String categoryId){
        return categoryRepository.findByCategoryId(categoryId);
    }

    @PostMapping("/create-category")
    public void createCategory(@RequestBody List<CategoryRequest> categoryRequests){
        for (CategoryRequest categoryRequest : categoryRequests) {
            saveCategoryRequest(categoryRequest, null, null, 1);
        }
    }

    private Category saveCategoryRequest(CategoryRequest categoryRequest, String categoryParentId, String categoryParentName, int level) {
        if(categoryRepository.findByName(categoryRequest.getName()) != null){
            return null;
        }

        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setCategoryParentId(categoryParentId);
        category.setCategoryParentName(categoryParentName);
        category.setLevel(level);

        category = categoryRepository.save(category);
        if (categoryRequest.getChildren() != null) {
            List<Category> children = new ArrayList<>();
            for (CategoryRequest childRequest : categoryRequest.getChildren()) {
                Category child = saveCategoryRequest(childRequest, category.getCategoryId(), category.getName() ,level + 1);
                children.add(child);
            }
            category.setChildren(children);
            categoryRepository.save(category);
        }

        return category;
    }
}
