package com.fpt.swp391.service;

import com.fpt.swp391.model.OrderItem;
import org.springframework.stereotype.Service;

@Service
public interface OrderItemService {
    OrderItem createOderItem(OrderItem orderItem);

    void addLaptopToCart(Long orderId, Long laptopId, int quantity);
}