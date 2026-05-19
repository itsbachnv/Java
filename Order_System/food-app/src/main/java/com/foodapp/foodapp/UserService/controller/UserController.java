package com.foodapp.foodapp.UserService.controller;

import com.foodapp.foodapp.UserService.JWT.JwtUtil;
import com.foodapp.foodapp.UserService.dto.LoginRequest;
import com.foodapp.foodapp.UserService.dto.LoginResponse;
import com.foodapp.foodapp.UserService.entity.User;
import com.foodapp.foodapp.UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAllUserInfo();
    }

    @GetMapping("/filter")
    public User findByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userService.findByEmail(request.getEmail());
        String jwtToken = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }
}
