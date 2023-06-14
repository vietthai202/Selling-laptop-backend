package com.fpt.swp391.service;

import com.fpt.swp391.model.Cart;
import com.fpt.swp391.model.CartItem;
import com.fpt.swp391.repository.CartItemRepository;
import com.fpt.swp391.repository.LaptopRepository;
import org.springframework.stereotype.Service;
import com.fpt.swp391.model.Laptop;


import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService{

    private final CartItemRepository cartItemRepository;

    private final LaptopRepository laptopRepository;

    private final CartService cartService;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, LaptopRepository laptopRepository, CartService cartService) {
        this.cartItemRepository = cartItemRepository;
        this.laptopRepository = laptopRepository;
        this.cartService = cartService;
    }

    @Override
    public CartItem getCartItemById(Long cartItemId) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        if (optionalCartItem.isPresent()) {
            return optionalCartItem.get();
        } else {
            throw new RuntimeException("CartItem not found with id: " + cartItemId);
        }
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }
}