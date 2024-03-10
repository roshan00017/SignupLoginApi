package org.example.signuplogin.security;

import org.example.signuplogin.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
    public interface JwtService {
        String extractUserName(String token);


        boolean isTokenValid(String token, UserDetails userDetails);

        String generateToken(User user);

        String generateRefreshToken();
    String extractEmail(String token);

}
