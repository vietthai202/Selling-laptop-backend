package com.fpt.swp391.controller;


import com.fpt.swp391.dto.ForgotRequest;
import com.fpt.swp391.dto.OrderDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Order;
import com.fpt.swp391.model.StatusEnum;
import com.fpt.swp391.service.LaptopService;
import com.fpt.swp391.service.OrderItemService;
import com.fpt.swp391.service.OrderService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final LaptopService laptopService;
    private final OrderItemService orderItemService;

    public OrderController(OrderService orderService, LaptopService laptopService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.laptopService = laptopService;
        this.orderItemService = orderItemService;
    }

    @GetMapping("/getOrders/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        Order o = orderService.getOrderbyId(id);
        if (o != null) {
            return ResponseEntity.status(HttpStatus.OK).body(0);
        }
        return null;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
        Order createdOrder = orderService.createOrder(orderDto);
        if (createdOrder != null) {
            return ResponseEntity.status(HttpStatus.OK).body(createdOrder);
        }
        return null;
    }

    @PostMapping("/{orderId}/add-laptop/{laptopId}/quantity")
    public ResponseEntity<?> addToOrder(@PathVariable Long orderId, @PathVariable Long laptopId, @RequestParam int quantity) {
        try {
            orderItemService.addLaptopToCart(orderId, laptopId, quantity);
            orderService.updateTotalPrice(orderId);
            return ResponseEntity.ok("Laptop has been added to the Order.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to add laptop to the Order.");
        }
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        try {
            StatusEnum statusEnum = StatusEnum.valueOf(status.toUpperCase());
            orderService.updateOrderStatus(orderId, statusEnum);
            return ResponseEntity.ok("Order status updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status provided.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update order status.");
        }
    }
    @PostMapping("/confirmKey")
    public ResponseEntity<?> confirmKey(@Valid @RequestBody ForgotRequest forgotRequest) {
        boolean sent = orderService.sendConfirmPassToEmail(forgotRequest.getEmail());
        if (sent) {
            final ApiSuccessResponse response = new ApiSuccessResponse("Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}