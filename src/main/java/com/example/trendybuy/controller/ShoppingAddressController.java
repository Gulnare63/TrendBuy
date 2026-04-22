package com.example.trendybuy.controller;

import com.example.trendybuy.dto.request.ShoppingAddressRequest;
import com.example.trendybuy.dto.response.ShoppingAddressResponse;
import com.example.trendybuy.service.ShoppingAdressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class ShoppingAddressController {

    private final ShoppingAdressService addressService;

    @PostMapping
    public ShoppingAddressResponse addAddress(@Valid @RequestBody ShoppingAddressRequest request) {
        return addressService.addAddress(request);
    }

    @PutMapping("/{id}")
    public ShoppingAddressResponse updateAddress(@PathVariable Long id, @Valid @RequestBody ShoppingAddressRequest request) {
        return addressService.updateAddress(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }

    @PostMapping("/{id}/default")
    public void setDefaultAddress(@PathVariable Long id) {
        addressService.setDefaultAddress(id);
    }

    @GetMapping
    public List<ShoppingAddressResponse> getMyAddresses() {
        return addressService.getMyAddresses();
    }
    
    @GetMapping("/default")
    public ShoppingAddressResponse getMyDefaultAddress() {
        return addressService.getMyDefaultAddress();
    }
}
