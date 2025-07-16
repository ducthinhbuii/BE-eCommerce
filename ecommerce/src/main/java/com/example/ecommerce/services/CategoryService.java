package com.example.ecommerce.services;

import java.util.List;

import com.example.ecommerce.dto.CategoryDto;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.request.CategoryRequest;

public interface CategoryService {
    
    /**
     * Lấy tất cả categories
     * @return danh sách categories
     */
    List<CategoryDto> getAllCategories();
    
    /**
     * Lấy top categories
     * @return danh sách top categories
     */
    List<CategoryDto> getTopCategories();
    
    /**
     * Lấy category theo name và parent
     * @param categoryName tên category
     * @param categoryParentName tên parent category
     * @return category
     */
    CategoryDto getCategoryByNameAndParent(String categoryName, String categoryParentName);
    
    /**
     * Lấy category theo ID
     * @param categoryId ID của category
     * @return danh sách categories
     */
    List<CategoryDto> getCategoryById(String categoryId);
    
    /**
     * Tạo categories từ danh sách requests
     * @param categoryRequests danh sách category requests
     */
    void createCategories(List<CategoryRequest> categoryRequests);
} 