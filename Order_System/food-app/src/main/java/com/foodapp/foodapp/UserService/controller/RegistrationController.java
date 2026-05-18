package com.foodapp.foodapp.UserService.controller;

import com.foodapp.foodapp.UserService.dto.RegistrationDto.*;
import com.foodapp.foodapp.UserService.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    /** Bước 1: đăng ký + gửi OTP */
    @PostMapping("/register")
    public ResponseEntity<OtpSentResponse> register(
            @Valid @RequestBody RegisterRequest req,
            HttpServletRequest http) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registrationService.register(req, getIp(http)));
    }

    /** Bước 2: xác thực OTP → nhận JWT */
    @PostMapping("/register/verify")
    public ResponseEntity<RegisterResponse> verify(
            @Valid @RequestBody VerifyOtpRequest req) {
        return ResponseEntity.ok(registrationService.verifyOtp(req));
    }

    /** Gửi lại OTP */
    @PostMapping("/register/resend-otp")
    public ResponseEntity<OtpSentResponse> resend(
            @Valid @RequestBody ResendOtpRequest req,
            HttpServletRequest http) {
        return ResponseEntity.ok(registrationService.resendOtp(req, getIp(http)));
    }

    // Lấy IP thật khi đứng sau proxy / load balancer
    private String getIp(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isBlank()) return ip.split(",")[0].trim();
        return req.getRemoteAddr();
    }
}