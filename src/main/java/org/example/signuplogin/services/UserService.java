package org.example.signuplogin.services;


import org.example.signuplogin.helper.Response.GeneralResponse;
import org.example.signuplogin.helper.Response.LoginResponse;
import org.example.signuplogin.entity.dto.LoginDto;
import org.example.signuplogin.entity.dto.RefreshTokenDto;
import org.example.signuplogin.entity.dto.SignupDto;
import org.example.signuplogin.entity.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    GeneralResponse signup(SignupDto signupDto);
    LoginResponse login(LoginDto loginDto);

    LoginResponse refreshToken(RefreshTokenDto tokenDto);

    UserDto getUserInfo();
}
