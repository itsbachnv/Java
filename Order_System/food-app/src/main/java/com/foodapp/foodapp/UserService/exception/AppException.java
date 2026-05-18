package com.foodapp.foodapp.UserService.exception;

// Base
public class AppException extends RuntimeException {
    private final String errorCode;
    public AppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    public String getErrorCode() { return errorCode; }
}

class DuplicateException extends AppException {
    public DuplicateException(String field) {
        super("DUPLICATE", field + " đã được đăng ký");
    }
}

class PasswordMismatchException extends AppException {
    public PasswordMismatchException() {
        super("PASSWORD_MISMATCH", "Mật khẩu xác nhận không khớp");
    }
}

class InvalidOtpException extends AppException {
    public InvalidOtpException(int remaining) {
        super("INVALID_OTP", "OTP không đúng, còn " + remaining + " lần thử");
    }
}

class OtpExpiredException extends AppException {
    public OtpExpiredException() {
        super("OTP_EXPIRED", "OTP đã hết hạn, vui lòng gửi lại");
    }
}

class OtpCooldownException extends AppException {
    public OtpCooldownException(long seconds) {
        super("OTP_COOLDOWN", "Vui lòng chờ " + seconds + " giây trước khi gửi lại");
    }
}

class OtpResendLimitException extends AppException {
    public OtpResendLimitException() {
        super("OTP_RESEND_LIMIT", "Đã vượt quá số lần gửi OTP trong ngày");
    }
}

class RateLimitException extends AppException {
    public RateLimitException() {
        super("RATE_LIMIT", "Quá nhiều yêu cầu, vui lòng thử lại sau");
    }
}

class UserNotFoundException extends AppException {
    public UserNotFoundException() {
        super("USER_NOT_FOUND", "Không tìm thấy người dùng");
    }
}