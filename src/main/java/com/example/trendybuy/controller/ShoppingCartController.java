package com.example.trendybuy.controller;

import com.example.trendybuy.dto.request.CartItemRequest;
import com.example.trendybuy.dto.response.CartItemResponse;
import com.example.trendybuy.service.ShoppingCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService cartService;

    @GetMapping
    public List<CartItemResponse> getMyCart() {
        return cartService.getCart();
    }

    @PostMapping("/add")
    public CartItemResponse addItemToCart(@Valid @RequestBody CartItemRequest request) {
        return cartService.addItemToCart(request);
    }

    @PutMapping("/item/{cartItemId}")
    public CartItemResponse updateQuantity(@PathVariable Long cartItemId, @RequestParam Integer quantity) {
        return cartService.updateCartItemQuantity(cartItemId, quantity);
    }

    @DeleteMapping("/item/{cartItemId}")
    public void removeItemFromCart(@PathVariable Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
    }

    @DeleteMapping("/clear")
    public void clearMyCart() {
        cartService.clearCart();
    }
}
