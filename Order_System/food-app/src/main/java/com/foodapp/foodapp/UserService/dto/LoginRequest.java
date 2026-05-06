package com.foodapp.foodapp.UserService.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
