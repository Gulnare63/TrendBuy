package com.example.trendybuy.service;

import com.example.trendybuy.dto.response.WishlistResponse;
import java.util.List;

public interface WishlistService {

    List<WishlistResponse> getMyWishlist();

    WishlistResponse toggleWishlist(Long productId);

    void clearWishlist();

    void moveWishlistItemToCart(Long productId);
}
