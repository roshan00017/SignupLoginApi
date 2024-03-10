package org.example.signuplogin.ServiceTest;

import org.example.signuplogin.entity.User;
import org.example.signuplogin.entity.dto.LoginDto;
import org.example.signuplogin.entity.dto.SignupDto;
import org.example.signuplogin.helper.Response.GeneralResponse;
import org.example.signuplogin.helper.Response.LoginResponse;
import org.example.signuplogin.repositories.RefreshTokenRepository;
import org.example.signuplogin.repositories.RoleRepository;
import org.example.signuplogin.repositories.UserRepository;
import org.example.signuplogin.security.JwtService;
import org.example.signuplogin.services.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignup() {
        // Prepare test data
        SignupDto signupDto = new SignupDto();
        signupDto.setDisplayName("Test User");
        signupDto.setEmail("test@example.com");
        signupDto.setPassword("testpassword");



        // Call the method under test
        GeneralResponse response = userService.signup(signupDto);

        // Assertions
        assertEquals("New User Created", response.getMessage());
    }

    @Test
    public void testLogin() {
        // Prepare test data
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("testpassword");

        User user = new User();
        user.setEmail(loginDto.getEmail());
        user.setPassword("hashedpassword");

        // Mock repository behavior
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(null);
        when(jwtService.generateToken(user)).thenReturn("mockedJWTToken");
        when(jwtService.generateRefreshToken()).thenReturn("mockedRefreshToken");
        when(refreshTokenRepository.findByUserId(1)).thenReturn(null);

        // Call the method under test
        LoginResponse response = userService.login(loginDto);

        // Assertions
        assertEquals(true, response.isFlag());
        assertEquals("Logged in Successfully !!!", response.getMessage());
        assertEquals("mockedJWTToken", response.getToken());
        assertEquals("mockedRefreshToken", response.getRefreshToken());
    }
}
