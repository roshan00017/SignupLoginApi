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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest("UserController.class")
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;



    @Test
    public void testSignup() {
        // Given
        SignupDto signupDto = new SignupDto();
        signupDto.setDisplayName("Test User");
        signupDto.setEmail("testuser@example.com");
        signupDto.setPassword("password123");

        GeneralResponse mockResponse = new GeneralResponse(true, "New User Created");

        when(userService.signup(signupDto)).thenReturn(mockResponse);

        // When
        ResponseEntity<GeneralResponse> responseEntity = userController.signup(signupDto);

        // Then
        verify(userService, times(1)).signup(signupDto);
        // Add more assertions based on your actual implementation and expected behavior
        // For example, check the status code, response body, etc.
        assertAll(() -> {
            assertEquals(true, responseEntity.getBody().isFlag());
            assertEquals("New User Created", responseEntity.getBody().getMessage());
        });

    }
    private SignupDto createTestSignupDto() {
        SignupDto signupDto = new SignupDto();
        signupDto.setDisplayName("Test User");
        signupDto.setEmail("test@example.com");
        signupDto.setPassword("testPassword");
        return signupDto;
    }


    @Test
    public void testLogin() throws Exception {
        // Given
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("testuser@example.com");
        loginDto.setPassword("password123");

        LoginResponse mockResponse = new LoginResponse(true, "Logged in Successfully !!!", "fakeJwtToken", "fakeRefreshToken");

        when(userService.login(loginDto)).thenReturn(mockResponse);

        ResponseEntity<LoginResponse> responseEntity = userController.login(loginDto);



        // Then
        verify(userService, times(1)).login(loginDto);
        // Add more assertions based on your actual implementation and expected behavior
        // For example, check the status code, response body, etc.
        assertAll(() -> {
            assertEquals(true, responseEntity.getBody().isFlag());
            assertEquals("Logged in Successfully !!!", responseEntity.getBody().getMessage());
            assertEquals("fakeJwtToken", responseEntity.getBody().getToken());
            assertEquals("fakeRefreshToken", responseEntity.getBody().getRefreshToken());
        });
    }

//    @Test
//    public void testGetUserInfo() throws Exception {
//        UserDto userDto = new UserDto();
//        userDto.setDisplayName("Test User");
//        userDto.setEmail("test@example.com");
//
//        when(userService.getUserInfo()).thenReturn(userDto);
//
//        mockMvc.perform(get("/api/userinfo"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.displayName").value("Test User"))
//                .andExpect(jsonPath("$.email").value("test@example.com"));
//
//        verify(userService, times(1)).getUserInfo();
//    }

//    @Test
//    public void testRefreshToken() throws Exception {
//        RefreshTokenDto refreshTokenDto = new RefreshTokenDto();
//        refreshTokenDto.setToken("refreshToken");
//
//        LoginResponse expectedResponse = new LoginResponse(true, "Token refreshed successfully", "jwtToken", "refreshToken");
//        when(userService.refreshToken(refreshTokenDto)).thenReturn(expectedResponse);
//
//        mockMvc.perform(post("/api/refreshtoken")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(refreshTokenDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.flag").value(true))
//                .andExpect(jsonPath("$.message").value("Token refreshed successfully"))
//                .andExpect(jsonPath("$.token").value("jwtToken"))
//                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));
//
//        verify(userService, times(1)).refreshToken(refreshTokenDto);
//    }
}
