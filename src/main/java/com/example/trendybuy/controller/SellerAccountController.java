package com.example.trendybuy.controller;

import com.example.trendybuy.dto.request.SellerRegisterRequest;
import com.example.trendybuy.dto.response.SellerProfileResponse;
import com.example.trendybuy.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerAccountController {

    private final SellerService sellerService;

    // 1) Seller register
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public SellerProfileResponse registerSeller(@Valid @RequestBody SellerRegisterRequest request) {
        return sellerService.registerSeller(request);
    }

    // 2) Öz seller profilini görmək (JWT ilə authenticated olanda)
    @GetMapping("/me")
    public SellerProfileResponse getMySellerProfile() {
        return sellerService.getMyProfile();
    }
}
