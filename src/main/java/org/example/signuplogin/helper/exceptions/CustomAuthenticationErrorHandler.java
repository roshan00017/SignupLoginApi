package org.example.signuplogin.helper.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//Handling Access Denied 403 and 401 Error
@Component
public class CustomAuthenticationErrorHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed: " + authException.getMessage());
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied: " + accessDeniedException.getMessage());
    }
}
