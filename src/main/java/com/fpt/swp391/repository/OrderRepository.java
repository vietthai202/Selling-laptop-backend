package com.fpt.swp391.repository;

import com.fpt.swp391.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOrderByEmail(String email);
}