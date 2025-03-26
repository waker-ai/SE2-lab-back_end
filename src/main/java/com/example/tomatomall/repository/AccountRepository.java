package com.example.tomatomall.repository;

import com.example.tomatomall.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}