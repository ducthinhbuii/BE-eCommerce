package com.example.ecommerce.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.request.AddItemRequest;

@Service
public class CartImpService implements CartService {
    private CartItemService cartItemService;
    private CartRepository cartRepository;
    private ProductService productService;

    public CartImpService(CartItemService cartItemService, CartRepository cartRepository,
            ProductService productService) {
        this.cartItemService = cartItemService;
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    public Cart createCart(Users user) {
        Cart cart = new Cart();
        cart.setUsers(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(String userId, AddItemRequest req) {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findById(req.getProductId());
        CartItem isPresent = cartItemService.isCartItemExists(cart, product, req.getSize(),userId);
        if(isPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart.getCartId());
            cartItem.setUserId(userId);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setSize(req.getSize());
            cartItem.setPrice(req.getQuantity() * product.getDiscountPrice());
            CartItem createCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createCartItem);
            cartRepository.save(cart);
        }
        return "Add Item to Cart";
    }

    @Override
    public Cart findUserCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId);

        int totalPrice = 0, totalDiscountPrice = 0, totalItem = 0;

        for(CartItem c: cart.getCartItems()){
            totalPrice += c.getPrice();
            totalDiscountPrice += c.getDiscountPrice();
            totalItem += c.getQuantity();
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountPrice(totalDiscountPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(totalPrice - totalDiscountPrice);
        return cartRepository.save(cart);
    }

    @Override
    public ArrayList<Cart> getAllCards() {
        return (ArrayList<Cart>) cartRepository.findAll();
    }
    
}
