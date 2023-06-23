package com.fpt.swp391.service;

import com.fpt.swp391.dto.OrderRequestDto;
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
        Optional<Laptop> laptopOptional = laptopRepository.findById(laptopId);
        if (laptopOptional.isPresent()) {
            Laptop l = laptopOptional.get();
            Optional<OrderItem> existingOrderItemOptional = order.getOrderItems().stream()
                    .filter(orderItem -> orderItem.getLaptop().equals(l))
                    .findFirst();
            if (existingOrderItemOptional.isPresent()) {
                OrderItem existingOrderItem = existingOrderItemOptional.get();
                existingOrderItem.setQuantity(existingOrderItem.getQuantity() + quantity);
                existingOrderItem.setPrice(existingOrderItem.getQuantity() * existingOrderItem.getLaptop().getPrice());
                orderItemRepository.save(existingOrderItem);
            } else {
                OrderItem newOrderItem = new OrderItem();
                newOrderItem.setOrder(order);
                newOrderItem.setLaptop(l);
                newOrderItem.setQuantity(quantity);
                newOrderItem.setPrice(newOrderItem.getQuantity() * newOrderItem.getLaptop().getPrice());
                order.getOrderItems().add(newOrderItem);
                orderItemRepository.save(newOrderItem);
            }
        }
    }

    @Override
    public boolean addMultiLaptopToCart(OrderRequestDto orderRequestDto) {
        try {
            Order order = orderService.getOrderbyId(orderRequestDto.getOrderId());
            String[] laptopIds = orderRequestDto.getLaptopIds().split(",");
            String[] quantities = orderRequestDto.getQuantities().split(",");
            if (order != null) {
                for (int i = 0; i < laptopIds.length; i++) {
                    Long lId = Long.valueOf(laptopIds[i]);
                    Optional<Laptop> laptopOptional = laptopRepository.findById(lId);
                    if (laptopOptional.isPresent()) {
                        Laptop l = laptopOptional.get();
                        Optional<OrderItem> existingOrderItemOptional = order.getOrderItems().stream()
                                .filter(orderItem -> orderItem.getLaptop().equals(l))
                                .findFirst();
                        if (existingOrderItemOptional.isPresent()) {
                            OrderItem existingOrderItem = existingOrderItemOptional.get();
                            existingOrderItem.setQuantity(existingOrderItem.getQuantity() + Integer.parseInt(quantities[i]));
                            Float newPrice= existingOrderItem.getLaptop().getPrice() - (existingOrderItem.getLaptop().getPrice() * existingOrderItem.getLaptop().getDiscount() / 100);
                            existingOrderItem.setPrice(existingOrderItem.getQuantity() * newPrice);
                            orderItemRepository.save(existingOrderItem);
                        } else {
                            OrderItem newOrderItem = new OrderItem();
                            newOrderItem.setOrder(order);
                            newOrderItem.setLaptop(l);
                            newOrderItem.setQuantity(Integer.parseInt(quantities[i]));
                            Float newPrice= newOrderItem.getLaptop().getPrice() - (newOrderItem.getLaptop().getPrice() * newOrderItem.getLaptop().getDiscount() / 100);
                            newOrderItem.setPrice(newOrderItem.getQuantity() * newPrice);
                            order.getOrderItems().add(newOrderItem);
                            orderItemRepository.save(newOrderItem);
                        }
                    }
                    orderService.updateTotalPrice(order.getId());
                }
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
}