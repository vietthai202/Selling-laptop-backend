package com.fpt.swp391.controller;

import com.fpt.swp391.dto.CartDto;
import com.fpt.swp391.model.Cart;
import com.fpt.swp391.model.CartItem;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.service.CartItemService;
import com.fpt.swp391.service.CartService;
import com.fpt.swp391.service.LaptopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    private final LaptopService laptopService;
    private final CartItemService cartItemService;

    public CartController(CartService cartService, LaptopService laptopService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.laptopService = laptopService;
        this.cartItemService = cartItemService;
    }

    @GetMapping("/getCart/{id}")
    public ResponseEntity<?> getCart(@PathVariable Long id){
        Cart c = cartService.getCartById(id);
        if (c!= null) {
            return ResponseEntity.status(HttpStatus.OK).body(c);
        }
        return null;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createCart(@RequestBody CartDto cartDto) {
        Cart createdCart = cartService.createCart(cartDto);
        if (createdCart != null) {
            return ResponseEntity.status(HttpStatus.OK).body(createdCart);
        }
        return null;
    }

    @PostMapping("/{cartId}/add-laptop/{laptopId}")
    public ResponseEntity<?> addToCart(@PathVariable Long cartId, @PathVariable Long laptopId) {
        try {
            Cart cart = cartService.getCartById(cartId);
            Laptop laptop = laptopService.getById(laptopId);
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setLaptop(laptop);
            cartItemService.createCartItem(cartItem);
            return ResponseEntity.ok("Laptop has been added to the cart.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to add laptop to the cart.");
        }
    }
}