package com.example.ecommerce.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.request.CreateProductRequest;

public interface ProductService {

	public Map<String, Object> fillterProducts(String category,Integer minPrice,Integer maxPrice,Integer minDiscount,String sort, Integer pageNumber, Integer pageSize, String name);

	public Product createProduct(CreateProductRequest req);

    List<Product> findAll();
	
	Product findById(String theId);

	public Map<String, Object>getAllProduct(String category, ArrayList<String> colors, ArrayList<String> sizes,
                                    Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock,
                                    Integer pageNumber, Integer pageSize, String name);
	
	void save(Product theProduct);
	
	void deleteById(String theId);

	Product updateProduct(String ProductId, Product req);
}
