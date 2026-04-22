package com.example.trendybuy.service;

import com.example.trendybuy.dto.request.CartItemRequest;
import com.example.trendybuy.dto.response.CartItemResponse;

import java.util.List;

public interface ShoppingCartService {

    List<CartItemResponse> getCart();

    CartItemResponse addItemToCart(CartItemRequest request);

    CartItemResponse updateCartItemQuantity(Long cartItemId, Integer quantity);

    void removeItemFromCart(Long cartItemId);

    void clearCart();
}
