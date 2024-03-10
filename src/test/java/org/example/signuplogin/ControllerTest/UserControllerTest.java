package org.example.signuplogin.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.signuplogin.helper.Response.GeneralResponse;
import org.example.signuplogin.helper.Response.LoginResponse;
import org.example.signuplogin.controllers.UserController;
import org.example.signuplogin.entity.dto.LoginDto;
import org.example.signuplogin.entity.dto.RefreshTokenDto;
import org.example.signuplogin.entity.dto.SignupDto;
import org.example.signuplogin.entity.dto.UserDto;
import org.example.signuplogin.repositories.UserRepository;
import org.example.signuplogin.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testSignup() throws Exception {
        SignupDto signupDto = new SignupDto();
        signupDto.setDisplayName("Test User");
        signupDto.setEmail("test@example.com");
        signupDto.setPassword("testpassword");

        GeneralResponse expectedResponse = new GeneralResponse(true, "New User Created");
        when(userService.signup(signupDto)).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signupDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.message").value("New User Created"));

        verify(userService, times(1)).signup(signupDto);
    }

    @Test
    public void testLogin() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("testpassword");

        LoginResponse expectedResponse = new LoginResponse(true, "Logged in Successfully !!!", "jwtToken", "refreshToken");
        when(userService.login(loginDto)).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.message").value("Logged in Successfully !!!"))
                .andExpect(jsonPath("$.token").value("jwtToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));

        verify(userService, times(1)).login(loginDto);
    }

    @Test
    public void testGetUserInfo() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setDisplayName("Test User");
        userDto.setEmail("test@example.com");

        when(userService.getUserInfo()).thenReturn(userDto);

        mockMvc.perform(get("/api/userinfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(userService, times(1)).getUserInfo();
    }

    @Test
    public void testRefreshToken() throws Exception {
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto();
        refreshTokenDto.setToken("refreshToken");

        LoginResponse expectedResponse = new LoginResponse(true, "Token refreshed successfully", "jwtToken", "refreshToken");
        when(userService.refreshToken(refreshTokenDto)).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/refreshtoken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(refreshTokenDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.message").value("Token refreshed successfully"))
                .andExpect(jsonPath("$.token").value("jwtToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));

        verify(userService, times(1)).refreshToken(refreshTokenDto);
    }
}
