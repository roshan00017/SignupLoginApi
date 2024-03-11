package org.example.signuplogin.controllers;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.example.signuplogin.helper.Response.GeneralResponse;
import org.example.signuplogin.helper.Response.LoginResponse;
import org.example.signuplogin.entity.dto.LoginDto;
import org.example.signuplogin.entity.dto.RefreshTokenDto;
import org.example.signuplogin.entity.dto.SignupDto;
import org.example.signuplogin.entity.dto.UserDto;
import org.example.signuplogin.repositories.UserRepository;
import org.example.signuplogin.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@SecurityRequirement(name="Bearer Authentication")
@RequestMapping("/api")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Registering New user
    @PostMapping("/signup")
    public ResponseEntity<GeneralResponse> signup(@Valid @RequestBody SignupDto signupDto) {

            GeneralResponse response = userService.signup(signupDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //User Login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDto loginDto) {

            LoginResponse response = userService.login(loginDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Logged in User Info
    @GetMapping("/userinfo")
    public ResponseEntity<UserDto> getUserInfo() {
            UserDto userDto = userService.getUserInfo();
            return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    // To get new refresh token for authentication
    @PostMapping("/refreshtoken")
    public ResponseEntity<LoginResponse> refreshtoken(@Valid @RequestBody RefreshTokenDto refreshTokenRequest) {
            LoginResponse response = userService.refreshToken(refreshTokenRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
