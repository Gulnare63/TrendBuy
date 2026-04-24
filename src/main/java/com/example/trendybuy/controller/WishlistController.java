package com.example.trendybuy.controller;

import com.example.trendybuy.dto.response.WishlistResponse;
import com.example.trendybuy.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public List<WishlistResponse> getMyWishlist() {
        return wishlistService.getMyWishlist();
    }

    @PostMapping("/toggle/{productId}")
    public WishlistResponse toggleWishlist(@PathVariable Long productId) {
        return wishlistService.toggleWishlist(productId);
    }

    @DeleteMapping("/clear")
    public void clearMyWishlist() {
        wishlistService.clearWishlist();
    }

    @PostMapping("/{productId}/move-to-cart")
    public void moveWishlistItemToCart(@PathVariable Long productId) {
        wishlistService.moveWishlistItemToCart(productId);
    }
}
