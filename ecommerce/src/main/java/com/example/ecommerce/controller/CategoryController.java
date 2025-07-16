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

import com.example.ecommerce.dto.CategoryDto;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.services.CategoryService;
import com.example.ecommerce.request.CategoryRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Operation(
        summary = "Get all categories",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of categories",
                content = @Content(schema = @Schema(implementation = CategoryDto.class))
            )
        }
    )
    @GetMapping("/get-all")
    public List<CategoryDto> getAllCategory(){
        return categoryService.getAllCategories();
    }

    @Operation(
        summary = "Get top categories",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Top categories",
                content = @Content(schema = @Schema(implementation = CategoryDto.class))
            )
        }
    )
    @GetMapping("/get-top-cat")
    public List<CategoryDto> getTopCategory(){
        return categoryService.getTopCategories();
    }

    @Operation(
        summary = "Get category by name and parent",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Category info",
                content = @Content(schema = @Schema(implementation = CategoryDto.class))
            )
        }
    )
    @GetMapping("/get-by-parent")
    public CategoryDto getCategoryByNameAndParent(
        @RequestParam(required = false) String categoryName,
        @RequestParam(required = false) String categoryParentName
        ){
        return categoryService.getCategoryByNameAndParent(categoryName, categoryParentName);
    }

    @Operation(
        summary = "Get category by id",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Category info",
                content = @Content(schema = @Schema(implementation = CategoryDto.class))
            )
        }
    )
    @GetMapping("/{categoryId}")
    public List<CategoryDto> getById(@PathVariable String categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    @Operation(
        summary = "Create new categories",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Categories created"
            )
        }
    )
    @PostMapping("/create-category")
    public void createCategory(@RequestBody List<CategoryRequest> categoryRequests){
        categoryService.createCategories(categoryRequests);
    }
}
