package com.example.trendybuy.service;

import com.example.trendybuy.dto.request.SellerRegisterRequest;
import com.example.trendybuy.dto.response.SellerProfileResponse;

import java.util.List;

public interface SellerService {

//    SellerProfileResponse registerSeller(SellerRegisterRequest request);

//    SellerProfileResponse getMyProfile();
    void registerSeller(SellerRegisterRequest request);
    List<SellerProfileResponse> getPendingSellers();
    void approveSeller(Long id);
    void rejectSeller(Long id);
}
