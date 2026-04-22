package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.ProductEntity;
import com.example.trendybuy.dao.entity.UserEntity;
import com.example.trendybuy.dao.entity.WishlistEntity;
import com.example.trendybuy.dao.repository.ProductRepository;
import com.example.trendybuy.dao.repository.UserRepository;
import com.example.trendybuy.dao.repository.WishlistRepository;
import com.example.trendybuy.dto.response.WishlistResponse;
import com.example.trendybuy.exception.ExceptionCode;
import com.example.trendybuy.exception.NotFoundException;
import com.example.trendybuy.mapper.WishlistMapper;
import com.example.trendybuy.service.WishlistService;
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
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final WishlistMapper wishlistMapper;

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<WishlistResponse> getMyWishlist() {
        UserEntity currentUser = getCurrentUser();
        List<WishlistEntity> wishlist = wishlistRepository.findByUser_UserId(currentUser.getUserId());
        return wishlistMapper.toResponseList(wishlist);
    }

    @Override
    public WishlistResponse toggleWishlist(Long productId) {
        UserEntity currentUser = getCurrentUser();

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.PRODUCT_NOT_FOUND));

        Optional<WishlistEntity> existingItem = wishlistRepository.findByUser_UserIdAndProduct_Id(currentUser.getUserId(), product.getId());

        if (existingItem.isPresent()) {
            wishlistRepository.delete(existingItem.get());
            return null; // Toggled off (silindi)
        } else {
            WishlistEntity newWishlist = new WishlistEntity();
            newWishlist.setUser(currentUser);
            newWishlist.setProduct(product);
            newWishlist.setAddedAt(LocalDateTime.now());
            WishlistEntity saved = wishlistRepository.save(newWishlist);
            return wishlistMapper.toResponse(saved);
        }
    }

    @Override
    public void clearWishlist() {
        UserEntity currentUser = getCurrentUser();
        wishlistRepository.deleteByUser_UserId(currentUser.getUserId());
    }

    private UserEntity getCurrentUser() {
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = Long.valueOf(userIdStr);

        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.USER_NOT_FOUND));
    }
}
