package com.foodapp.foodapp.UserService.controller;

import com.foodapp.foodapp.UserService.dto.LoginRequest;
import com.foodapp.foodapp.UserService.dto.LoginResponse;
import com.foodapp.foodapp.UserService.entity.User;
import com.foodapp.foodapp.UserService.service.UserService;
import com.foodapp.foodapp.common.JWT.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {
    private UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAllUserInfo();
    }

    @GetMapping("/filter")
    public Optional<User> findByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Optional<User> user = userService.findByEmail(request.getEmail());
        String jwtToken = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody LoginRequest request) {

    }
}
