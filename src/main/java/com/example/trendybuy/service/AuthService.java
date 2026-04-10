package com.example.trendybuy.service;

import com.example.trendybuy.dto.request.*;

public interface AuthService {

    void registerWithEmail(EmailRegisterRequest request);

    void registerWithPhone(PhoneRegisterRequest request);

    AuthResponse verifyEmailOtp(VerifyEmailOtpRequest request);

    AuthResponse verifyPhoneOtp(VerifyPhoneOtpRequest request);

    void login(LoginRequest request);

    AuthResponse verifyLoginOtp(VerifyLoginOtpRequest request);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);

    void changePassword(ChangePasswordRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

//    UserDto getCurrentUser();
}
