package com.example.ecommerce.services;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

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
        CartItem isPresent = cartItemService.isCartItemExists(userId, req.getProductId());
        if(isPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart.getCartId());
            cartItem.setUserId(userId);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setSize(req.getSize());
            cartItem.setPrice(req.getQuantity() * req.getPrice());
            cartItem.setDiscountPrice(req.getQuantity() * req.getDiscountPrice());
            CartItem createCartItem = cartItemService.createCartItem(cartItem);

            cart.getCartItems().add(createCartItem);
            cart.setTotalPrice(cart.getTotalPrice() + createCartItem.getPrice());
            cart.setTotalDiscountPrice(cart.getTotalDiscountPrice() + createCartItem.getDiscountPrice());
            cart.setTotalItem(cart.getTotalItem() + 1);
            cart.setDiscount(cart.getTotalPrice() - cart.getTotalDiscountPrice());
            cartRepository.save(cart);
        } else {
            isPresent.setQuantity(isPresent.getQuantity() + req.getQuantity());
            isPresent.setPrice(isPresent.getPrice() + req.getQuantity() * req.getPrice());
            isPresent.setDiscountPrice(isPresent.getDiscountPrice() + req.getQuantity() * req.getDiscountPrice());
            cartItemService.createCartItem(isPresent);

            cart.setTotalPrice(cart.getTotalPrice() + req.getQuantity() * req.getPrice());
            cart.setTotalDiscountPrice(cart.getTotalDiscountPrice() + req.getQuantity() * req.getDiscountPrice());
            cart.setDiscount(cart.getTotalPrice() - cart.getTotalDiscountPrice());
            cartRepository.save(cart);
            
        }
        return "Add Item to Cart";
    }

    @Override
    public String removeProductCart(String userId, AddItemRequest req){
        Cart cart = cartRepository.findByUserId(userId);
        CartItem isPresent = cartItemService.isCartItemExists(userId, req.getProductId());
        if(isPresent != null){
            if(isPresent.getQuantity() > req.getQuantity()){
                cartItemService.removeProductCartItem(isPresent, req);
            } else if (isPresent.getQuantity() == req.getQuantity()){
                cart.setTotalItem(cart.getTotalItem() - 1);
                Set<CartItem> temp = cart.getCartItems().stream().filter(cartItem -> !cartItem.getCartItemId().equals(isPresent.getCartItemId())).collect(Collectors.toSet());
                cart.setCartItems(temp);
                cartItemService.removeCartItem(isPresent);
            } else {
                return "quantity is not reliable";
            }
        }

        cart.setTotalPrice(cart.getTotalPrice() - req.getPrice() * req.getQuantity());
        cart.setTotalDiscountPrice(cart.getTotalDiscountPrice() - req.getDiscountPrice() * req.getQuantity());
        cart.setDiscount(cart.getTotalPrice() - cart.getTotalDiscountPrice());
        cartRepository.save(cart);
        return "remove product cart success";
    }

    @Override
    public String removeCartItem(String userId, AddItemRequest req){
        Cart cart = cartRepository.findByUserId(userId);
        CartItem isPresent = cartItemService.isCartItemExists(userId, req.getProductId());
        if(isPresent != null){
            int quantity = 0;
            quantity = isPresent.getQuantity();
            cart.setTotalItem(cart.getTotalItem() - 1);
            Set<CartItem> temp = cart.getCartItems().stream().filter(cartItem -> !cartItem.getCartItemId().equals(isPresent.getCartItemId())).collect(Collectors.toSet());
            cart.setCartItems(temp);
            cartItemService.removeCartItem(isPresent);
            cart.setTotalPrice(cart.getTotalPrice() - req.getPrice() * quantity);
            cart.setTotalDiscountPrice(cart.getTotalDiscountPrice() - req.getDiscountPrice() * quantity);
            cart.setDiscount(cart.getTotalPrice() - cart.getTotalDiscountPrice());
            cartRepository.save(cart);
        }

        return "remove cart items success";
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

    @Override
    public Cart getCartByUserId(String userId){
        return cartRepository.findByUserId(userId);
    }
    
}
