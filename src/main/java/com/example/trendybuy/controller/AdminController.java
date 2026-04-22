package com.example.trendybuy.controller;

import com.example.trendybuy.dto.response.SellerProfileResponse;
import com.example.trendybuy.service.SellerService;
import com.example.trendybuy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final SellerService sellerService;

    @PostMapping("/users/{id}/deactivate")
    public void blockUser(@PathVariable Long id) {
        userService.deactivateUser(id);
    }

    @PostMapping("/users/{id}/activate")
    public void unblockUser(@PathVariable Long id) {
        userService.activateUser(id);
    }

    @GetMapping("/sellers/pending")
    public List<SellerProfileResponse> getPendingSellers() {
        return sellerService.getPendingSellers();
    }

    @PostMapping("/sellers/{id}/approve")
    public void approveSeller(@PathVariable Long id) {
        sellerService.approveSeller(id);
    }

    @PostMapping("/sellers/{id}/reject")
    public void rejectSeller(@PathVariable Long id) {
        sellerService.rejectSeller(id);
    }
}
