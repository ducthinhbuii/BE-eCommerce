package com.example.ecommerce.request;

import java.util.List;

public class CategoryRequest {
    private String name;
    private List<CategoryRequest> children;
    public CategoryRequest() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<CategoryRequest> getChildren() {
        return children;
    }
    public void setChildren(List<CategoryRequest> children) {
        this.children = children;
    } 
}
