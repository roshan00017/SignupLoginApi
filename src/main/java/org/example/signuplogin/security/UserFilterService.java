package org.example.signuplogin.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service()
public interface UserFilterService {
    UserDetailsService userDetailsService();
}