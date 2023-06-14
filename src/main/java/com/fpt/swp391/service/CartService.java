package com.fpt.swp391.service;

import com.fpt.swp391.dto.CartDto;
import com.fpt.swp391.model.Cart;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    Cart createCart(CartDto cartDto);

    Cart getCartById(Long cartId);
}