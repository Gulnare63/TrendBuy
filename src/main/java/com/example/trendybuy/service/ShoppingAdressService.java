package com.example.trendybuy.service;

import com.example.trendybuy.dto.request.ShoppingAddressRequest;
import com.example.trendybuy.dto.response.ShoppingAddressResponse;

import java.util.List;

public interface ShoppingAdressService {

    ShoppingAddressResponse addAddress(ShoppingAddressRequest request);

    ShoppingAddressResponse updateAddress(Long id, ShoppingAddressRequest request);

    void deleteAddress(Long id);

    void setDefaultAddress(Long id);

    List<ShoppingAddressResponse> getMyAddresses();
    
    ShoppingAddressResponse getMyDefaultAddress();
}
