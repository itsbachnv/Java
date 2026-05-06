package com.foodapp.foodapp.UserService.service;

import com.foodapp.foodapp.UserService.entity.User;
import com.foodapp.foodapp.UserService.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUserInfo() {
        return userRepository.findAll().stream().toList();
    }
}
