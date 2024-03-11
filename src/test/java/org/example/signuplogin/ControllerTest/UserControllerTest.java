package org.example.signuplogin.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.signuplogin.entity.SecUser;
import org.example.signuplogin.entity.SystemRole;
import org.example.signuplogin.entity.User;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        assertAll(() -> {
            assertEquals(true, responseEntity.getBody().isFlag());
            assertEquals("Logged in Successfully !!!", responseEntity.getBody().getMessage());
            assertEquals("fakeJwtToken", responseEntity.getBody().getToken());
            assertEquals("fakeRefreshToken", responseEntity.getBody().getRefreshToken());
        });
    }


    @Test
    public void testRefreshToken() throws Exception {
        // Given
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto();
        refreshTokenDto.setToken("fakeRefreshToken");

        LoginResponse mockResponse = new LoginResponse(true, "Token refreshed successfully", "fakeJwtToken", "fakeRefreshToken");

        when(userService.refreshToken(refreshTokenDto)).thenReturn(mockResponse);

        // When
        when(userService.refreshToken(refreshTokenDto)).thenReturn(mockResponse);
        ResponseEntity<LoginResponse> responseEntity = userController.refreshtoken(refreshTokenDto);

        // Then
        verify(userService, times(1)).refreshToken(refreshTokenDto);

        assertAll(() -> {
            assertEquals(true, responseEntity.getBody().isFlag());
            assertEquals("Token refreshed successfully", responseEntity.getBody().getMessage());
            assertEquals("fakeJwtToken", responseEntity.getBody().getToken());
            assertEquals("fakeRefreshToken", responseEntity.getBody().getRefreshToken());

        });
    }


    @Test
    public void testGetUserInfo() throws Exception {
        // Given
        UserDto mockUserDto = new UserDto();
        mockUserDto.setDisplayName("Test User");
        mockUserDto.setEmail("testuser@example.com");
        mockUserDto.setRole("ADMIN");

        Authentication authentication = Mockito.mock(Authentication.class);
        SecUser secUser = Mockito.mock(SecUser.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(secUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Mocking the behavior of SecUser and UserService
        Mockito.when(secUser.getUser()).thenReturn(new User());
        Mockito.when(secUser.getUserRole()).thenReturn(new SystemRole());
        Mockito.when(userService.getUserInfo()).thenReturn(mockUserDto);

        // When
        ResponseEntity<UserDto> responseEntity = userController.getUserInfo();
        // Then
        Mockito.verify(userService, Mockito.times(1)).getUserInfo();
        // Add more assertions based on your actual implementation and expected behavior
        // For example, check the status code, response body, etc.
        assertAll(() -> {
            assertEquals("Test User", responseEntity.getBody().getDisplayName());
            assertEquals("testuser@example.com", responseEntity.getBody().getEmail());
            assertEquals("ADMIN", responseEntity.getBody().getRole());
            // Add assertions for additional fields as needed
        });
    }
}
