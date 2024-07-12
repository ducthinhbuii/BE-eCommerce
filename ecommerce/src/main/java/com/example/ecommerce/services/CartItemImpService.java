package com.example.ecommerce.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.UserRepository;

@Service
public class CartItemImpService implements CartItemService{

    private CartItemRepository cartItemRepository;
    private UserRepository userRepository;
    private CartRepository cartRepository;

    public CartItemImpService(CartItemRepository cartItemRepository, UserRepository userRepository,
            CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountPrice(cartItem.getProduct().getDiscountPrice() * cartItem.getQuantity());
        CartItem createCartItem = cartItemRepository.save(cartItem);
        return createCartItem;
    }

    @Override
    public CartItem updateCartItem(String userId, String id, CartItem cartItem) {
        CartItem cartItem2 = findCartItemById(id);

        Optional<Users> temp = userRepository.findById(cartItem2.getUserId());
        if(temp.isPresent()){
            Users user = temp.get();
            if(user.getId().equals(userId)){
                cartItem2.setQuantity(cartItem.getQuantity());
                cartItem2.setPrice(cartItem2.getProduct().getPrice() * cartItem2.getQuantity());
                cartItem2.setDiscountPrice(cartItem2.getProduct().getDiscountPrice() * cartItem2.getQuantity());
            }
        }
        return cartItemRepository.save(cartItem2);

    }

    @Override
    public CartItem isCartItemExists(Cart cart, Product product, String size, String userId) {
        CartItem cartItem = cartItemRepository.isCartItemExists(cart, product, size, userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(String cartItemId, String userId) {
        CartItem cartItem = findCartItemById(cartItemId);
        if(cartItem.getUserId().equals(userId)){
            cartItemRepository.delete(cartItem);
        } else {
            throw new UnsupportedOperationException("You can't remove another users item"); 
        }
        
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
