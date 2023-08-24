package com.example.backend.auth;

import com.example.backend.user.dtos.UserDetails;
import com.example.backend.user.entities.User;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class AuthService {
    public Boolean validateUser(UserDetails user, String password) {
        String hashedPassword = hashPassword(password);

        return user.getPasswordHash().equals(hashedPassword);
    }

    public String generateToken(User user) {
        return null;
    }

    public String hashPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
