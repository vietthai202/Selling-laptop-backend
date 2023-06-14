package com.fpt.swp391.service;

import com.fpt.swp391.dto.OrderDto;
import com.fpt.swp391.model.*;
import com.fpt.swp391.repository.OrderRepository;
import com.fpt.swp391.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.fpt.swp391.utils.SendMail;


import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TransactionService transactionService;
    private final SendMail sendMail;


    Set<OrderItem> orderItems = new HashSet<>();


    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, TransactionService transactionService, SendMail sendMail) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.transactionService = transactionService;
        this.sendMail = sendMail;
    }

    @Override
    public Order createOrder(OrderDto orderDto) {
        Order newOrder = new Order();
        Long userId = orderDto.getUserId();
        User user = userRepository.findById(userId).orElse(null);
        newOrder.setUser(user);
        newOrder.setFirstName(orderDto.getFirstName());
        newOrder.setLastName(orderDto.getLastName());
        newOrder.setEmail(orderDto.getEmail());
        newOrder.setCity(orderDto.getCity());
        newOrder.setPhoneNumber(orderDto.getPhoneNumber());
        Date currentDate = new Date();
        newOrder.setCreatedAt(currentDate);
        newOrder.setStatus(StatusEnum.WAIT.toString());
        newOrder.setProvince(orderDto.getProvince());
        newOrder.setTotalPrice(0.0F);
        orderRepository.save(newOrder);
        return newOrder;
    }

    @Override
    public Order getOrderbyId(Long id) {
        Optional<Order> optionalCart = orderRepository.findById(id);
        if (optionalCart.isPresent()) {
            return optionalCart.get();
        } else {
            throw new RuntimeException("Cart not found with id: " + id);
        }
    }

    @Override
    public void updateOrderStatus(Long orderId, StatusEnum status) {
        Order order = getOrderbyId(orderId);
        if (order != null) {
            order.setStatus(String.valueOf(status));
            orderRepository.save(order);
            if (status == StatusEnum.DONE) {
                createTransactionForOrder(order);
            }
        }
    }

    @Override
    public void updateTotalPrice(Long orderId) {
        Order o = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        orderItems = o.getOrderItems();
        float totalPrice = 0.0f;
        for (OrderItem orderItem : orderItems) {
            float itemPrice = orderItem.getPrice();
            totalPrice += itemPrice;
        }
        o.setTotalPrice(totalPrice);
        orderRepository.save(o);
    }

    private void createTransactionForOrder(Order order) {
        User user = order.getUser();
        String content = "Transaction for order #" + order.getId();
        Transaction transaction = transactionService.createTransaction(StatusEnum.DONE, order, user, content);
    }

    @Override
    public boolean sendConfirmPassToEmail(String email) {
        try {
            Order o = orderRepository.findOrderByEmail(email);
            String confirmKey = generateRandomString();
            String bodyEmail = "Confirm key là " + confirmKey;
            sendMail.sendMailRender(o.getEmail(), "CONFIRM KEY FOR ORDER", bodyEmail);
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    private String generateRandomString() {
        int length = 5;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }
        return randomString.toString();
    }

}