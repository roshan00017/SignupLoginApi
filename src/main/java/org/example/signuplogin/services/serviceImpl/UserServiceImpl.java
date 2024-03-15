package org.example.signuplogin.services.serviceImpl;



import lombok.extern.slf4j.Slf4j;
import org.example.signuplogin.entity.*;
import org.example.signuplogin.helper.Constants;
import org.example.signuplogin.helper.Response.LoginResponse;
import org.example.signuplogin.helper.Response.GeneralResponse;
import org.example.signuplogin.entity.dto.LoginDto;
import org.example.signuplogin.entity.dto.RefreshTokenDto;
import org.example.signuplogin.entity.dto.SignupDto;
import org.example.signuplogin.entity.dto.UserDto;
import org.example.signuplogin.repositories.RefreshTokenRepository;
import org.example.signuplogin.repositories.RoleRepository;
import org.example.signuplogin.repositories.UserRepository;
import org.example.signuplogin.repositories.UserRoleRepository;
import org.example.signuplogin.security.JwtService;
import org.example.signuplogin.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service()
    public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    private final RoleRepository roleRepository;


    private final RefreshTokenRepository refreshTokenRepository;


    private final UserRoleRepository userRoleRepository;


    private final JwtService jwtService;


    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, RefreshTokenRepository refreshTokenRepository, UserRoleRepository userRoleRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRoleRepository = userRoleRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public GeneralResponse signup(SignupDto signupDto) {
        try {

            // Check if user with the same email already exists
            if (userRepository.findByEmail(signupDto.getEmail()) != null) {
                log.warn("User with email {} already exists. Registration failed.", signupDto.getEmail());
                return new GeneralResponse(false, "User has Been Already Created");
            }

            //Setting Up new User Details
            User newUser = new User();
            newUser.setDisplayName(signupDto.getDisplayName());
            newUser.setEmail(signupDto.getEmail());
            newUser.setPassword(passwordEncoder.encode(signupDto.getPassword()));
            newUser.setCreatedAt(new Date());

            //Saving User
            User savedUser = userRepository.save(newUser);

            //Giving Role to the Newly created User

            if (roleRepository.findByName(Constants.Admin) == null) {
                // Create Admin Role if not exists
                SystemRole adminRole = new SystemRole();
                adminRole.setName(Constants.Admin);
                roleRepository.save(adminRole);

                //give admin role to first user
                UserRole user = new UserRole();
                user.setUserId(savedUser.getId());
                user.setRoleId(adminRole.getId());
                userRoleRepository.save(user);
                log.info("New user with email {} registered as Admin.", savedUser.getEmail());
                return new GeneralResponse(true, "New User Created");

            }


            if (roleRepository.findByName(Constants.User) == null) {
                // Create User Role if not exists
                SystemRole userRole = new SystemRole();
                userRole.setName(Constants.User);
                roleRepository.save(userRole);
                //give user role to second user
                UserRole user = new UserRole();
                user.setUserId(savedUser.getId());
                user.setRoleId(userRole.getId());
                userRoleRepository.save(user);
                log.info("New user with email {} registered as User.", savedUser.getEmail());
                return new GeneralResponse(true, "New User Created");

            } else {
                // Giving user role to all others
                UserRole user = new UserRole();
                user.setUserId(savedUser.getId());
                user.setRoleId(roleRepository.findByName(Constants.User).getId());
                userRoleRepository.save(user);
                log.info("New user with email {} registered as User.", savedUser.getEmail());
                return new GeneralResponse(true, "New User Created");
            }

        } catch (Exception e) {
            log.error("Error occurred during user registration: {}", e.getMessage(), e);
            return new GeneralResponse(false, "Error occurred: " + e.getMessage());
        }

    }

    @Transactional
    @Override
    public LoginResponse login(LoginDto loginDto) {
        try {
            //Checking email
            if (loginDto.getEmail() == null) {
                log.warn("Login failed. Email is required.");
                return new LoginResponse(false, "Email Required !!!");
            }
            User user = userRepository.findByEmail(loginDto.getEmail());
            //Check if user Exist
            if (user == null) {
                log.warn("Login failed. User with email {} does not exist.", loginDto.getEmail());
                return new LoginResponse(false, "Oops User Doesnot Exist !!!");
            }
            // Check Password/Email
            if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                log.warn("Login failed. Incorrect password for user with email {}.", loginDto.getEmail());
                return new LoginResponse(false, "Try Again! Email/Password Not Valid !!!");
            }
            //Checking if User Has No Role
            if (userRoleRepository.findByUserId(user.getId()) == null) {
                log.warn("Login failed. User with email {} does not have any role.", loginDto.getEmail());
                return new LoginResponse(false, "Oops User Doesnot Have Any Role !!!");
            }

            //Generating the Jwt Token and Refresh Token
            String jwtToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken();

            RefreshTokenInfo existingTokenInfo = refreshTokenRepository.findByUserId(user.getId());
            //    Checking if user has refresh token
            if (existingTokenInfo != null) {
                //Save if not
                existingTokenInfo.setToken(refreshToken);
                refreshTokenRepository.save(existingTokenInfo);
            } else {
                //Update if it has
                RefreshTokenInfo refreshTokenInfo = new RefreshTokenInfo();
                refreshTokenInfo.setToken(refreshToken);
                refreshTokenInfo.setUserId(user.getId());
                refreshTokenRepository.save(refreshTokenInfo);
            }
            log.info("User with email {} logged in successfully.", user.getEmail());

            return new LoginResponse(true, "Logged in Successfully !!!", jwtToken, refreshToken);


        } catch (Exception e) {
            log.error("Error occurred during login: {}", e.getMessage(), e);
            return new LoginResponse(false, "Error occurred: " + e.getMessage());
        }

    }

    @Override
    public UserDto getUserInfo() {

        try {
            // Getting logged in user info
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SecUser secUser = (SecUser) authentication.getPrincipal();
            User user = secUser.getUser();
            SystemRole systemRole = secUser.getUserRole();
            UserDto userDto = new UserDto();
            userDto.setDisplayName(user.getDisplayName());
            userDto.setEmail(user.getEmail());
            userDto.setRole(systemRole.getName());

            log.info("User info retrieved successfully for user with email {}", user.getEmail());
            return userDto;
        } catch (Exception e) {
            log.error("Error occurred while retrieving user info: {}", e.getMessage(), e);
            return null;
        }


    }

    //Generating refresh Token

    @Transactional

    @Override
    public LoginResponse refreshToken(RefreshTokenDto tokenDto) {
        try {
            RefreshTokenInfo findToken = refreshTokenRepository.findByToken(tokenDto.getToken());
            if (findToken == null) {
                log.warn("Invalid refresh token: {}", tokenDto.getToken());
                return new LoginResponse(false, "Invalid Refresh token ");
            }
            User user = userRepository.findById(findToken.getUserId());
            if (user == null) {
                log.warn("User not found for refresh token: {}", tokenDto.getToken());
                return new LoginResponse(false, "Oops User Doesnot Exist! Refresh token cannot be generated !! ");
            }

            String jwtToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken();

            RefreshTokenInfo updateRefreshToken = refreshTokenRepository.findByUserId(user.getId());
            if (updateRefreshToken == null) {
                log.warn("Refresh token could not be generated because user has not signed in: {}", user.getEmail());
                return new LoginResponse(false, "Refresh token could not be generated because user has not signed in");
            }
            updateRefreshToken.setToken(refreshToken);
            refreshTokenRepository.save(updateRefreshToken);
            log.info("Token refreshed successfully for user: {}", user.getEmail());
            return new LoginResponse(true, "Token refreshed successfully", jwtToken, refreshToken);
        } catch (Exception e) {
            log.error("Error occurred while refreshing token: {}", e.getMessage(), e);
            return new LoginResponse(false, "Error occurred while refreshing token");
        }

    }
}


