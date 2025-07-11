package com.example.ecommerce.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.request.CreateProductRequest;
import com.example.ecommerce.services.implement.ProductImpService;
import com.example.ecommerce.dto.ProductDto;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductImpService productImpService;
    
    public ProductController(ProductImpService productImpService){
        this.productImpService = productImpService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return ResponseEntity.ok(productImpService.findAll());
    }

    @PostMapping("/create-product")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody CreateProductRequest req){
        return ResponseEntity.ok(productImpService.createProduct(req));
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getFilterProduct(@RequestParam(required = false) String category,@RequestParam(required = false) ArrayList<String> colors,@RequestParam(required = false) ArrayList<String> sizes,
                                   @RequestParam(required = false) Integer minPrice,@RequestParam(required = false) Integer maxPrice,@RequestParam(required = false) Integer minDiscount,@RequestParam(required = false) String sort,@RequestParam(required = false) String stock,
                                   @RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String name)
    {
        Map<String, Object> results = productImpService.getAllProduct(category, colors, sizes, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize, name);

        return ResponseEntity.ok(results);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId){
        return ResponseEntity.ok(productImpService.findById(productId));
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable String productId, @RequestBody Product req){
        ProductDto updatedProduct = productImpService.updateProduct(productId, req);
        return ResponseEntity.ok(updatedProduct);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Product> getProductById(@PathVariable String id){
    //     Optional<Product> product = productRepository.findById(id);
    //     if(product.isPresent()){
    //         return ResponseEntity.ok(product.get());
    //     }
    //     return ResponseEntity.notFound().build();
    // }

    // @GetMapping("/{category}")
    // public ResponseEntity<Product> getProductByCategory(@PathVariable String category){
    //     Optional<Product> product = productRepository.findByCategory(category);
    //     if(product.isPresent()){
    //         return ResponseEntity.ok(product.get());
    //     }
    //     return ResponseEntity.notFound().build();
    // }
                            
    // @DeleteMapping("/products/{id}")
    // public ResponseEntity<Void> deleteProduct(@PathVariable String id){
    //     productRepository.deleteById(id);
    //     return ResponseEntity.noContent().build();
    // }
}
