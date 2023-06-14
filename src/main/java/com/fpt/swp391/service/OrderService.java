package com.fpt.swp391.service;

import com.fpt.swp391.dto.OrderDto;
import com.fpt.swp391.model.Order;
import com.fpt.swp391.model.StatusEnum;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Order createOrder(OrderDto orderDto);

    Order getOrderbyId(Long id);

    void updateOrderStatus(Long orderId, StatusEnum newStatus);
    void updateTotalPrice(Long id);
    boolean sendConfirmPassToEmail(String email);
}