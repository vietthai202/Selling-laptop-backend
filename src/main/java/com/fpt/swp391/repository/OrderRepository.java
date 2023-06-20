package com.fpt.swp391.repository;

import com.fpt.swp391.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOrderByEmail(String email);
    @Query("SELECT DISTINCT o FROM Order o JOIN o.user u WHERE u.username = :userName")
    Set<Order> findOrdersByUserName(String userName);
}