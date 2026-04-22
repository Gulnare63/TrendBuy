package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.ProductEntity;
import com.example.trendybuy.dao.entity.ShoppingCartEntity;
import com.example.trendybuy.dao.entity.UserEntity;
import com.example.trendybuy.dao.repository.ProductRepository;
import com.example.trendybuy.dao.repository.ShoppingCartRepository;
import com.example.trendybuy.dao.repository.UserRepository;
import com.example.trendybuy.dto.request.CartItemRequest;
import com.example.trendybuy.dto.response.CartItemResponse;
import com.example.trendybuy.exception.ExceptionCode;
import com.example.trendybuy.exception.NotFoundException;
import com.example.trendybuy.mapper.CartMapper;
import com.example.trendybuy.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<CartItemResponse> getCart() {
        UserEntity currentUser = getCurrentUser();
        List<ShoppingCartEntity> cartItems = cartRepository.findByUser_UserId(currentUser.getUserId());
        return cartMapper.toResponseList(cartItems);
    }

    @Override
    public CartItemResponse addItemToCart(CartItemRequest request) {
        UserEntity currentUser = getCurrentUser();

        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException(ExceptionCode.PRODUCT_NOT_FOUND));

        if (!product.isActive()) {
            throw new IllegalArgumentException("Product is not active or available.");
        }

        if (product.getStockQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
        }

        // Karta eyni məhsuldan əvvəlcədən varsa, sayını artırırıq
        Optional<ShoppingCartEntity> existingItemOpt = cartRepository.findByUser_UserIdAndProduct_Id(currentUser.getUserId(), product.getId());

        ShoppingCartEntity cartItem;
        if (existingItemOpt.isPresent()) {
            cartItem = existingItemOpt.get();
            int newQuantity = cartItem.getQuantity() + request.getQuantity();
            if (product.getStockQuantity() < newQuantity) {
                throw new IllegalArgumentException("Not enough stock. You already have " + cartItem.getQuantity() + " in cart.");
            }
            cartItem.setQuantity(newQuantity);
            cartItem.setAddedAt(LocalDateTime.now());
        } else {
            cartItem = new ShoppingCartEntity();
            cartItem.setUser(currentUser);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setAddedAt(LocalDateTime.now());
        }

        ShoppingCartEntity saved = cartRepository.save(cartItem);
        return cartMapper.toResponse(saved);
    }

    @Override
    public CartItemResponse updateCartItemQuantity(Long cartItemId, Integer quantity) {
        UserEntity currentUser = getCurrentUser();

        ShoppingCartEntity cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.VALIDATION_ERROR)); // xususi exception yaza bilerik

        if (!cartItem.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new IllegalArgumentException("You are not authorized to modify this cart item");
        }

        if (quantity <= 0) {
            cartRepository.delete(cartItem);
            return null; // və ya xüsusi mesaj qaytara bilərdik
        }

        if (cartItem.getProduct().getStockQuantity() < quantity) {
             throw new IllegalArgumentException("Not enough stock for product: " + cartItem.getProduct().getName());
        }

        cartItem.setQuantity(quantity);
        ShoppingCartEntity updated = cartRepository.save(cartItem);
        
        return cartMapper.toResponse(updated);
    }

    @Override
    public void removeItemFromCart(Long cartItemId) {
        UserEntity currentUser = getCurrentUser();

        ShoppingCartEntity cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.VALIDATION_ERROR)); 

        if (!cartItem.getUser().getUserId().equals(currentUser.getUserId())) {
             throw new IllegalArgumentException("You are not authorized to remove this cart item");
        }

        cartRepository.delete(cartItem);
    }

    @Override
    public void clearCart() {
        UserEntity currentUser = getCurrentUser();
        cartRepository.deleteByUser_UserId(currentUser.getUserId());
    }

    // ============================ Helper Metodlar ============================

    private UserEntity getCurrentUser() {
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = Long.valueOf(userIdStr);

        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.USER_NOT_FOUND));
    }
}
