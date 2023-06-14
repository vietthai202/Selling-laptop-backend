package com.fpt.swp391.service;


import com.fpt.swp391.model.CartItem;
import org.springframework.stereotype.Service;

@Service
public interface CartItemService {
    CartItem getCartItemById(Long cartItemId);

    CartItem createCartItem(CartItem cartItem);
}