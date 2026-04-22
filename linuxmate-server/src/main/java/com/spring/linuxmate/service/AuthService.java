package com.spring.linuxmate.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean validateUser(String username, String password) {
        return username.equals("admin") && password.equals("admin123");
    }
}