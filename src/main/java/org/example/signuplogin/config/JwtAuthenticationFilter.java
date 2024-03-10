package org.example.signuplogin.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.signuplogin.entity.User;
import org.example.signuplogin.security.JwtService;
import org.example.signuplogin.security.UserFilterService;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//JWT Auhtentication Filter for Token Validation in Request Header

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private  final JwtService jwtService;

    private final UserFilterService userFilterService;

    public JwtAuthenticationFilter(JwtService jwtService, UserFilterService userFilterService) {
        this.jwtService = jwtService;
        this.userFilterService = userFilterService;


    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final  String jwtToken;
        final String userEmail;
        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
        userEmail = jwtService.extractEmail(jwtToken);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userFilterService.userDetailsService()
                    .loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                // Create UsernamePasswordAuthenticationToken without authorities
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null);
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }
}