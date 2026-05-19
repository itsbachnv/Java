package com.foodapp.foodapp.UserService.service;

import com.foodapp.foodapp.UserService.JWT.JwtUtil;
import com.foodapp.foodapp.UserService.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtil jwtUtil;

    @Value("${jwt.expiration}")
    private long accessTokenExpiry; // đơn vị milliseconds

    public String generateAccessToken(User user) {
        return jwtUtil.generateToken(user);
    }

    /**
     * Refresh token — JwtUtil chưa hỗ trợ riêng,
     * tạm thời dùng chung logic, sau này tách ra nếu cần.
     */
    public String generateRefreshToken(User user) {
        return jwtUtil.generateToken(user);
    }

    /** Trả về số giây (convert từ ms) để điền vào response */
    public long getAccessTokenExpiry() {
        return accessTokenExpiry / 1000;
    }
}