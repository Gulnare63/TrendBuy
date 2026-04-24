package com.example.trendybuy.controller;

import com.example.trendybuy.dto.request.SellerRegisterRequest;
import com.example.trendybuy.dto.response.SellerProfileResponse;
import com.example.trendybuy.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerAccountController {

    private final SellerService sellerService;

    @PostMapping("/register")
    public String registerSeller(@RequestBody SellerRegisterRequest request) {
        sellerService.registerSeller(request);
        return "Seller registration submitted. Pending approval";
    }

    @GetMapping("/pending")
    public List<SellerProfileResponse> getPendingSellers() {
        return sellerService.getPendingSellers();
    }

    @PutMapping("/{id}/approve")
    public String approveSeller(@PathVariable Long id) {
        sellerService.approveSeller(id);
        return "Seller approved";
    }

    @PutMapping("/{id}/reject")
    public String rejectSeller(@PathVariable Long id) {
        sellerService.rejectSeller(id);
        return "Seller rejected";
    }


}
