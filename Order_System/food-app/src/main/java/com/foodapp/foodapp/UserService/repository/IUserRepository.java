package com.foodapp.foodapp.UserService.repository;

import com.foodapp.foodapp.UserService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    List<User> findByEmail(String email); //auto

    @Query("Select u from User u where u.email like concat('%',:email, '%')")
    User findByEmailCustom(@Param("email") String email);

}
