package com.fpt.swp391.repository;

import com.fpt.swp391.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}