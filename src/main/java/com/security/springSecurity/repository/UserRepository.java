package com.security.springSecurity.repository;


import com.security.springSecurity.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String phoneNumber);
    //SELECT * FROM users WHERE phoneNumber=?
}

