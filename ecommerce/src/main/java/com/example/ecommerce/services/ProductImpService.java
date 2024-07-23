package com.example.ecommerce.services;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

@Service
public class ProductImpService implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SizeRepository sizeRepository;

    public ProductImpService(ProductRepository productRepository, CategoryRepository categoryRepository,
                            SizeRepository sizeRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.sizeRepository = sizeRepository;
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Product createProduct(CreateProductRequest req){
        List<Category> categories = categoryRepository.findByCategoryId(req.getCategoryId());
        Category category = categories == null ? null : categories.get(0);
        
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
        return savedProduct;
    }

    @Override
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Override
    public Product findById(String theId) {
        Optional<Product> result = productRepository.findById(theId);
		
		Product theProduct = null;
		if (result.isPresent()) {
			theProduct = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find employee id - " + theId);
		}
		return theProduct;
    }

    @Override
    public List<Product>getAllProduct(String category, ArrayList<String> colors, ArrayList<String> sizes,
                                    Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock,
                                    Integer pageNumber, Integer pageSize)
    {
        List<Product> products = fillterProducts(category, minPrice, maxPrice, minDiscount, sort, pageNumber, pageSize);
        if(colors != null){
            products = products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
                        .collect(Collectors.toList());
        }

        if(stock != null){
            if(stock.equalsIgnoreCase("in-stock")){
                products = products.stream().filter(p->p.getQuantity() > 0).collect(Collectors.toList());
            } else if (stock.equalsIgnoreCase("out-of-stock")){
                products = products.stream().filter(p->p.getQuantity() < 1).collect(Collectors.toList());
            }
        }
        return products;
    }

    @Override
    public Product updateProduct(String ProductId, Product req){
        Optional<Product> productOp = productRepository.findById(ProductId);
        if(productOp != null){
            Product product = productOp.get();
            Category category = categoryRepository.findByCategoryId(req.getCategory().getCategoryId()).get(0);
            product.setCategory(category);
            return productRepository.save(product);
        } else {
            return null;
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
    public List<Product> fillterProducts(String categoryId, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, Integer pageNumber, Integer pageSize) {
        Query query = new Query();

        String tempId = categoryId;
        if (tempId != null){
            List<Criteria> criterias = new ArrayList<>();
            queryFindProductByChildCat(categoryId, criterias);
            Criteria combinedCriteria = new Criteria().orOperator(criterias.toArray(new Criteria[0]));
            query.addCriteria(combinedCriteria);
            // query.addCriteria(Criteria.where("category.categoryId").in(categoryIds));
        }

        // if(categoryId != null){
        //     query.addCriteria(Criteria.where("category.categoryId").is(categoryId));
        // }

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

        if("price_low".equalsIgnoreCase(sort)){
            query.with(Sort.by(Sort.Order.asc("discountPrice")));
        }
        if("price_high".equalsIgnoreCase(sort)){
            query.with(Sort.by(Sort.Order.desc("discountPrice")));
        }

        if(pageNumber != null && pageSize != null){
            Integer skip = pageNumber * pageSize;
            query.skip(skip).limit(pageSize);
        }
        return mongoTemplate.find(query, Product.class);
    }
}
