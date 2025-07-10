package com.example.ecommerce.services.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecommerce.exception.ConflictException;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.request.CategoryRequest;
import com.example.ecommerce.services.CategoryService;

@Service
public class CategoryImpService implements CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    public CategoryImpService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    @Override
    public List<Category> getTopCategories() {
        return categoryRepository.findTopCategory();
    }
    
    @Override
    public Category getCategoryByNameAndParent(String categoryName, String categoryParentName) {
        Category category = categoryRepository.findByNameAndParent(categoryName, categoryParentName);
        if (category == null) {
            throw new ResourceNotFoundException("Category", "name and parent", 
                categoryName + " with parent " + categoryParentName);
        }
        return category;
    }
    
    @Override
    public List<Category> getCategoryById(String categoryId) {
        List<Category> categories = categoryRepository.findByCategoryId(categoryId);
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("Category", "id", categoryId);
        }
        return categories;
    }
    
    @Override
    public void createCategories(List<CategoryRequest> categoryRequests) {
        for (CategoryRequest categoryRequest : categoryRequests) {
            saveCategoryRequest(categoryRequest, null, null, 1);
        }
    }
    
    private Category saveCategoryRequest(CategoryRequest categoryRequest, String categoryParentId, 
                                       String categoryParentName, int level) {
        // Business validation: Kiểm tra category name đã tồn tại chưa
        if (categoryRepository.findByName(categoryRequest.getName()) != null) {
            throw new ConflictException("Category with name '" + categoryRequest.getName() + "' already exists");
        }
        
        // Business logic: Tạo category mới
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setCategoryParentId(categoryParentId);
        category.setCategoryParentName(categoryParentName);
        category.setLevel(level);
        
        category = categoryRepository.save(category);
        
        // Business logic: Tạo children categories nếu có
        if (categoryRequest.getChildren() != null) {
            List<Category> children = new ArrayList<>();
            for (CategoryRequest childRequest : categoryRequest.getChildren()) {
                Category child = saveCategoryRequest(childRequest, category.getCategoryId(), 
                                                  category.getName(), level + 1);
                if (child != null) {
                    children.add(child);
                }
            }
            category.setChildren(children);
            categoryRepository.save(category);
        }
        
        return category;
    }
} 