package com.example.ecommerce.controller;

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

    @GetMapping("/get-by-parent")
    public Category getCategoryByNameAndParent(
        @RequestParam(required = false) String categoryName,
        @RequestParam(required = false) String categoryParentName
        ){
        return categoryRepository.findByNameAndParent(categoryName, categoryParentName);
    }

    @GetMapping("/{categoryId}")
    public Category getById(@PathVariable String categoryId){
        return categoryRepository.findByCategoryId(categoryId);
    }

    @PostMapping("/create-category")
    public void createCategory(@RequestBody List<CategoryRequest> listReq){
        System.out.println("create category");
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
