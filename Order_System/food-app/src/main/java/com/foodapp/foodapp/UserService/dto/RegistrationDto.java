package com.foodapp.foodapp.UserService.dto;

import lombok.Data;
import org.hibernate.annotations.processing.Pattern;

public class RegistrationDto {

    // ── Requests ──────────────────────────────────────────────

    /** Bước 1: nhập thông tin + gửi OTP */
    @Data
    public static class RegisterRequest {

        @NotBlank
        @Email(message = "Email không hợp lệ")
        private String email;

        @Pattern(
                regexp = "^(0)(3[2-9]|5[6-9]|7[0|6-9]|8[0-9]|9[0-9])[0-9]{7}$",
                message = "Số điện thoại không hợp lệ"
        )
        private String phone;

        @NotBlank
        @Size(min = 8, max = 50)
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
                message = "Mật khẩu phải có chữ hoa, chữ thường, số và ký tự đặc biệt"
        )
        private String password;

        @NotBlank(message = "Xác nhận mật khẩu không được để trống")
        private String confirmPassword;

        @AssertTrue(message = "Bạn phải đồng ý với điều khoản sử dụng")
        private Boolean agreedToTerms;

        private String referralCode; // optional
    }

    /** Bước 2: xác thực OTP */
    @Data
    public static class VerifyOtpRequest {
        @NotBlank
        private String identifier; // email hoặc SĐT

        @NotBlank
        @Pattern(regexp = "^[0-9]{6}$", message = "OTP gồm 6 chữ số")
        private String otp;
    }

    /** Resend OTP */
    @Data
    public static class ResendOtpRequest {
        @NotBlank
        private String identifier;
    }

    // ── Responses ─────────────────────────────────────────────

    @Data
    @lombok.Builder
    public static class OtpSentResponse {
        private String maskedIdentifier;
        private String message;
        private long otpExpiresInSeconds;
        private long cooldownSeconds;
    }

    @Data
    @lombok.Builder
    public static class RegisterResponse {
        private String accessToken;
        private String refreshToken;
        private long expiresIn;
        private UserSummary user;
    }

    @Data
    @lombok.Builder
    public static class UserSummary {
        private String id;
        private String email;
        private String phone;
        private String firstName;
        private String lastName;
        private String referralCode;
    }
}