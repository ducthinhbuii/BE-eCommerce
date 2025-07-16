package com.example.ecommerce.services.implement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.ecommerce.services.CartService;
import com.example.ecommerce.services.OrderService;
import com.example.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Payment;
import com.example.ecommerce.model.PaymentDetails;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.AddressRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.PaymentDetailRepository;
import com.example.ecommerce.repository.UserRepository;

@Service
public class OrderImpService implements OrderService {

    private CartService cartService;
    private ProductService productService;
    private AddressRepository addressRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private OrderItemRepository orderItemRepository;
    private PaymentDetailRepository paymentDetailRepository;

    public OrderImpService(CartService cartService, ProductService productService,
            AddressRepository addressRepository,OrderRepository orderRepository,
            UserRepository userRepository, OrderItemRepository orderItemRepository,
            PaymentDetailRepository paymentDetailRepository) {
        this.cartService = cartService;
        this.productService = productService;
        this.addressRepository = addressRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentDetailRepository = paymentDetailRepository;
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Order createOrder(Users user, Address shippingAddress) {
        Address address = addressRepository.isAddressExists(shippingAddress.getStreet(), shippingAddress.getCity(), shippingAddress.getState());
        Order order = new Order();
        if(address == null){
            Address address2 = new Address();
            address2.setStreet(shippingAddress.getStreet());
            address2.setCity(shippingAddress.getCity());
            address2.setState(shippingAddress.getState());
            addressRepository.save(address2);
            user.getListAddress().add(address2);
            userRepository.save(user);
            order.setShippingAddress(address2);
        } else {
            order.setShippingAddress(address);
        }

        Cart cart = cartService.findUserCart(user.getId());
        order.setCart(cart);

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPaymentStatus("Pending");
        paymentDetailRepository.save(paymentDetails);
        order.setPaymentDetails(paymentDetails);

        order.setOrderStatus("Pending");
        order.setCreateAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        return savedOrder;

    }

    @Override
    public Order findOrderById(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        return order;
    }

    @Override
    public List<Order> usersOrderHistory(String userId) {
        List<Order> orders = getUsersOrder(userId);
        return orders;
    }

    @Override
    public Order placedOrder(String orderId) {
        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setPaymentStatus("COMPLETED");
        return order;
    }

    @Override
    public Order confirmOrder(String orderId) {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(String orderId) {
        Order order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(String orderId) {
        Order order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return orderRepository.save(order);
    }

    @Override
    public Order canceledOrder(String orderId) {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CANCELED");
        return orderRepository.save(order);
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        return (ArrayList<Order>) orderRepository.findAll();
    }

    @Override
    public void deleteOrder(String orderId) {
        Order order = findOrderById(orderId);
        if(order != null){
            orderRepository.deleteById(orderId);
        }
    }

    @Override
    public List<Order> getUsersOrder(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user.userId").is(userId));
        Criteria criteria = new Criteria().orOperator(
            Criteria.where("orderStatus").is("PLACED"),
            Criteria.where("orderStatus").is("CONFIRMED"),
            Criteria.where("orderStatus").is("SHIPPED"),
            Criteria.where("orderStatus").is("DELIVERED")
        );
        query.addCriteria(criteria);
        return mongoTemplate.find(query, Order.class);
    }
    
}
