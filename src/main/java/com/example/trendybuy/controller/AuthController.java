package com.example.trendybuy.controller;

import com.example.trendybuy.dto.request.*;
import com.example.trendybuy.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/email")
    public MessageResponse registerEmail(@Valid @RequestBody EmailRegisterRequest request) {
        authService.registerWithEmail(request);
        return new MessageResponse("OTP sent to email");
    }


    @PostMapping("/verify/email-otp")
    public AuthResponse verifyEmailOtp(@Valid @RequestBody VerifyEmailOtpRequest request) {
        return authService.verifyEmailOtp(request);
    }


    @PostMapping("/register/phone")
    public MessageResponse registerPhone(@Valid @RequestBody PhoneRegisterRequest request) {
        authService.registerWithPhone(request);
        return new MessageResponse("OTP sent to phone");
    }


    @PostMapping("/verify/phone-otp")
    public AuthResponse verifyPhoneOtp(@Valid @RequestBody VerifyPhoneOtpRequest request) {
        return authService.verifyPhoneOtp(request);
    }

    @PostMapping("/login")
    public MessageResponse login(@Valid @RequestBody LoginRequest request) {
        authService.login(request);
        return new MessageResponse("Login OTP sent");
    }


    @PostMapping("/login/verify-otp")
    public AuthResponse verifyLoginOtp(@Valid @RequestBody VerifyLoginOtpRequest request) {
        return authService.verifyLoginOtp(request);
    }

    @PostMapping("/forgot-password")
    public MessageResponse forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request);
        return new MessageResponse("If account exists, OTP sent");
    }


    @PostMapping("/reset-password")
    public MessageResponse resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return new MessageResponse("Password successfully reset");
    }

    @PostMapping("/change-password")
    public MessageResponse changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(request);
        return new MessageResponse("Password successfully changed");
    }

//    bunu  usere kecirmeyi unutma
//    @GetMapping("/me")
//    public UserDto getCurrentUser()
//    {
//        return authService.getCurrentUser();
//    }




    @PostMapping("/refresh")
    public AuthResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public MessageResponse logout() {
        return new MessageResponse("Successfully logged out");
    }
}
