package com.foodapp.foodapp.UserService.controller;

import com.foodapp.foodapp.UserService.entity.User;
import com.foodapp.foodapp.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAllUserInfo();
    }

    @GetMapping("/filter")
    public Optional<User> findByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }
}
