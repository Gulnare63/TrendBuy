package com.example.trendybuy.service;

import com.example.trendybuy.dto.request.SellerRegisterRequest;
import com.example.trendybuy.dto.response.SellerProfileResponse;

public interface SellerService {

    SellerProfileResponse registerSeller(SellerRegisterRequest request);

    SellerProfileResponse getMyProfile();
}
