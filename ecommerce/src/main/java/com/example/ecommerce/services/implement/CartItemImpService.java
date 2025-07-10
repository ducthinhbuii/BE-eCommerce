package com.example.ecommerce.services.implement;

import java.util.Optional;

import com.example.ecommerce.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.request.AddItemRequest;

@Service
public class CartItemImpService implements CartItemService {

    private CartItemRepository cartItemRepository;
    private UserRepository userRepository;
    private CartRepository cartRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public CartItemImpService(CartItemRepository cartItemRepository, UserRepository userRepository,
            CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        // cartItem.setQuantity(1);
        // cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        // cartItem.setDiscountPrice(cartItem.getProduct().getDiscountPrice() * cartItem.getQuantity());
        CartItem createCartItem = cartItemRepository.save(cartItem);
        return createCartItem;
    }

    @Override
    public CartItem updateCartItem(String userId, String id, CartItem cartItem) {
        CartItem cartItem2 = findCartItemById(id);

        if(cartItem2.getUserId().equals(userId)){
            cartItem2.setQuantity(cartItem.getQuantity());
            cartItem2.setPrice(cartItem.getPrice());
            cartItem2.setDiscountPrice(cartItem.getDiscountPrice());
        }
        return cartItemRepository.save(cartItem2);

    }

    @Override
    public CartItem isCartItemExists(String userId, String productId) {
        CartItem cartItem = getCartItemExists(userId, productId);
        return cartItem;
    }

    public CartItem getCartItemExists(String userId, String productId){
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("productId").is(productId));
        CartItem cartItem = mongoTemplate.findOne(query, CartItem.class);
        return cartItem;
    }


    @Override
    public String removeCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
        return "remove success";
    }

    @Override
    public String removeProductCartItem(CartItem cartItem, AddItemRequest req){
        cartItem.setQuantity(cartItem.getQuantity() - req.getQuantity());
        cartItem.setPrice(cartItem.getPrice() - req.getPrice() * req.getQuantity());
        cartItem.setDiscountPrice(cartItem.getDiscountPrice() - req.getDiscountPrice() * req.getQuantity());
        cartItemRepository.save(cartItem);
        return "remove success";
    }

    @Override
    public CartItem findCartItemById(String cartItemId) {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if(cartItem.isPresent()){
            return cartItem.get();
        }
        return null;
    }
    
}
