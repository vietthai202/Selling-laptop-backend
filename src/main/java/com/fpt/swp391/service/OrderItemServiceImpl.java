package com.fpt.swp391.service;

import com.fpt.swp391.model.*;
import com.fpt.swp391.repository.LaptopRepository;
import com.fpt.swp391.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    public final OrderItemRepository orderItemRepository;
    public final OrderService orderService;
    public final LaptopRepository laptopRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderService orderService, LaptopRepository laptopRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.laptopRepository = laptopRepository;
    }

    @Override
    public OrderItem createOderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void addLaptopToCart(Long orderId, Long laptopId, int quantity) {
        Order order = orderService.getOrderbyId(orderId);
        OrderItem orderItem = new OrderItem();
        Optional<Laptop> laptopOptional = laptopRepository.findById(laptopId);
        if (laptopOptional.isPresent()) {
            Laptop l = laptopOptional.get();
            orderItem.setOrder(order);
            orderItem.setLaptop(l);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(orderItem.getQuantity() * orderItem.getLaptop().getPrice());
        }
        orderItemRepository.save(orderItem);
    }
}