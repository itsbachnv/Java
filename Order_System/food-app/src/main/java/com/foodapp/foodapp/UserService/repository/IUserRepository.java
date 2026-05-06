package com.foodapp.foodapp.UserService.repository;

import com.foodapp.foodapp.UserService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, String> { }
