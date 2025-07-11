package com.example.ecommerce.services.implement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.example.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Size;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.SizeRepository;
import com.example.ecommerce.request.CreateProductRequest;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.mapper.ProductMapper;
import com.example.ecommerce.dto.ProductDto;

@Service
public class ProductImpService implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SizeRepository sizeRepository;
    private final ProductMapper productMapper;

    public ProductImpService(ProductRepository productRepository, CategoryRepository categoryRepository,
                            SizeRepository sizeRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.sizeRepository = sizeRepository;
        this.productMapper = productMapper;
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ProductDto createProduct(CreateProductRequest req){
        // Business validation: Kiểm tra category có tồn tại không
        List<Category> categories = categoryRepository.findByCategoryId(req.getCategoryId());
        if (categories == null || categories.isEmpty()) {
            throw new ResourceNotFoundException("Category", "id", req.getCategoryId());
        }
        Category category = categories.get(0);
        
        // Category topLevel = categoryRepository.findByName(req.getTopLeverCategory());
        // if(topLevel == null){
        //     Category topLevelCategory = new Category();
        //     topLevelCategory.setName(req.getTopLeverCategory());
        //     topLevelCategory.setLevel(1);

        //     topLevel = categoryRepository.save(topLevelCategory);

        // }

        // Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLeverCategory(),topLevel.getName());
        // if(secondLevel == null){
        //     Category secondLevelCategory = new Category();
        //     secondLevelCategory.setName(req.getSecondLeverCategory());
        //     secondLevelCategory.setCategoryParent(topLevel);
        //     secondLevelCategory.setLevel(2);

        //     secondLevel = categoryRepository.save(secondLevelCategory);
        // }

        // Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLeverCategory(),secondLevel.getName());
        // if(thirdLevel == null){
        //     Category thirdLevelCategory = new Category();
        //     thirdLevelCategory.setName(req.getThirdLeverCategory());
        //     thirdLevelCategory.setCategoryParent(secondLevel);
        //     thirdLevelCategory.setLevel(3);

        //     thirdLevel = categoryRepository.save(thirdLevelCategory);
        // }
        
        System.out.println("create product");
        Product product = new Product();
        product.setName(req.getName());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountPercent(req.getDiscountPercent());
        product.setDiscountPrice(req.getDiscountPrice());
        product.setImgUrl(req.getImgUrl());
        product.setPrice(req.getPrice());
        product.setBrand(req.getBrand());
        product.setQuantity(req.getQuantity());
        product.setCreateAt(LocalDateTime.now());
        product.setCategory(category);

        int quantity = 0;
        Set<Size> size = new HashSet<>();
        for(Size s: req.getSize()){
            Size temp = new Size();
            temp.setName(s.getName());
            quantity += s.getQuantity();
            temp.setQuantity(s.getQuantity());
            sizeRepository.save(temp);
            size.add(temp);
        }
        product.setQuantity(quantity);
        product.setSize(size);

        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public List<ProductDto> findAll(){
        List<Product> products = productRepository.findAll();
        return productMapper.toDtoList(products);
    }

    @Override
    public ProductDto findById(String theId) {
        Optional<Product> result = productRepository.findById(theId);

        if (result.isPresent()) {
            return productMapper.toDto(result.get());
        } else {
            throw new ResourceNotFoundException("Product", "id", theId);
        }
    }
    
    @Override
    public Product findProductById(String theId) {
        Optional<Product> result = productRepository.findById(theId);
		
		if (result.isPresent()) {
			return result.get();
		}
		else {
			throw new ResourceNotFoundException("Product", "id", theId);
		}
    }

    @Override
    public Map<String, Object> getAllProduct(String category, ArrayList<String> colors, ArrayList<String> sizes,
                                    Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock,
                                    Integer pageNumber, Integer pageSize, String name)
    {
        Map<String, Object> results = fillterProducts(category, minPrice, maxPrice, minDiscount, sort, pageNumber, pageSize, name);
        // if(colors != null){
        //     products = products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
        //                 .collect(Collectors.toList());
        // }

        // if(stock != null){
        //     if(stock.equalsIgnoreCase("in-stock")){
        //         products = products.stream().filter(p->p.getQuantity() > 0).collect(Collectors.toList());
        //     } else if (stock.equalsIgnoreCase("out-of-stock")){
        //         products = products.stream().filter(p->p.getQuantity() < 1).collect(Collectors.toList());
        //     }
        // }
        return results;
    }

    @Override
    public ProductDto updateProduct(String ProductId, Product req){
        Optional<Product> productOp = productRepository.findById(ProductId);
        if(productOp.isPresent()){
            Product product = productOp.get();
            Category category = categoryRepository.findByCategoryId(req.getCategory().getCategoryId()).get(0);
            product.setCategory(category);
            Product updatedProduct = productRepository.save(product);
            return productMapper.toDto(updatedProduct);
        } else {
            throw new ResourceNotFoundException("Product", "id", ProductId);
        }
    }

    @Override
    public void save(Product theProduct) {
        productRepository.save(theProduct);
    }

    @Override
    public void deleteById(String theId) {
        productRepository.deleteById(theId);
    }

    public void queryFindProductByChildCat(String categoryId, List<Criteria> criterias){
        criterias.add(Criteria.where("category.categoryId").is(categoryId));
        Category category = categoryRepository.findByCategoryId(categoryId).get(0);
        if(category.getChildren() != null){
            for(Category child: category.getChildren()){
                queryFindProductByChildCat(child.getCategoryId(), criterias);
            }
        }
    }

    @Override
    public Map<String, Object> fillterProducts(String categoryId, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, Integer pageNumber, Integer pageSize, String name) {
        Query query = new Query();

        String tempId = categoryId;
        if (tempId != null){
            List<Criteria> criterias = new ArrayList<>();
            queryFindProductByChildCat(categoryId, criterias);
            Criteria combinedCriteria = new Criteria().orOperator(criterias.toArray(new Criteria[0]));
            query.addCriteria(combinedCriteria);
        }

        if (minPrice != null || maxPrice != null) {
            Criteria priceCriteria = Criteria.where("discountPrice");

            if (minPrice != null) {
                priceCriteria = priceCriteria.gte(minPrice);
            }

            if (maxPrice != null && maxPrice > 0) {
                priceCriteria = priceCriteria.lte(maxPrice);
            }

            query.addCriteria(priceCriteria);
        }

        if(minDiscount != null){
            query.addCriteria(Criteria.where("discountPercent").gte(minDiscount));
        }

        if (name != null) {
            query.addCriteria(Criteria.where("name").regex(".*" + name + ".*", "iu"));
        }

        if("price_low".equalsIgnoreCase(sort)){
            query.with(Sort.by(Sort.Order.asc("discountPrice")));
        }
        if("price_high".equalsIgnoreCase(sort)){
            query.with(Sort.by(Sort.Order.desc("discountPrice")));
        }

        long totalProduct = mongoTemplate.count(query, Product.class);

        if(pageNumber != null && pageSize != null){
            Integer skip = pageNumber * pageSize;
            query.skip(skip).limit(pageSize);
        }

        List<Product> products = mongoTemplate.find(query, Product.class);
        
        Map<String, Object> result = new HashMap<>();
        result.put("totalCount", totalProduct);
        result.put("products", products);
        return result;
    }
}
