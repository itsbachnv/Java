package com.foodapp.foodapp.UserService.controller;

import com.foodapp.foodapp.UserService.entity.User;
import com.foodapp.foodapp.UserService.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private UserService userService;

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAllUserInfo();
    }
}
