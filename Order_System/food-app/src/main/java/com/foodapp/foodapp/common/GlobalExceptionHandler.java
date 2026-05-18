package com.foodapp.foodapp.common;

import com.foodapp.foodapp.UserService.exception.AppExceptions.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    record ErrorResponse(String errorCode, String message, Object details) {}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(e ->
                errors.put(((FieldError) e).getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("VALIDATION_ERROR", "Dữ liệu không hợp lệ", errors));
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleApp(AppException ex) {
        HttpStatus status = switch (ex.getErrorCode()) {
            case "DUPLICATE"         -> HttpStatus.CONFLICT;
            case "RATE_LIMIT",
                 "OTP_COOLDOWN",
                 "OTP_RESEND_LIMIT"  -> HttpStatus.TOO_MANY_REQUESTS;
            case "USER_NOT_FOUND"    -> HttpStatus.NOT_FOUND;
            default                  -> HttpStatus.BAD_REQUEST;
        };
        log.warn("[APP_ERROR] {} - {}", ex.getErrorCode(), ex.getMessage());
        return ResponseEntity.status(status)
                .body(new ErrorResponse(ex.getErrorCode(), ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("[ERROR] Unexpected: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_ERROR", "Đã có lỗi xảy ra, vui lòng thử lại", null));
    }
}