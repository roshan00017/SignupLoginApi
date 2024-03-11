//package org.example.signuplogin.ServiceTest;
//
//import org.example.signuplogin.entity.User;
//import org.example.signuplogin.entity.dto.LoginDto;
//import org.example.signuplogin.entity.dto.SignupDto;
//import org.example.signuplogin.helper.Response.GeneralResponse;
//import org.example.signuplogin.helper.Response.LoginResponse;
//import org.example.signuplogin.repositories.RefreshTokenRepository;
//import org.example.signuplogin.repositories.RoleRepository;
//import org.example.signuplogin.repositories.UserRepository;
//import org.example.signuplogin.repositories.UserRoleRepository;
//import org.example.signuplogin.security.JwtService;
//import org.example.signuplogin.services.UserService;
//import org.example.signuplogin.services.serviceImpl.UserServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class UserServiceImplTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private UserRoleRepository userRoleRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private RefreshTokenRepository refreshTokenRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    @Test
//    public void testLogin() {
//        // Given
////        LoginDto loginDto = new LoginDto();
////        loginDto.setEmail("testuser@example.com");
////        loginDto.setPassword("password123");
////
////        User existingUser = new User();
////        existingUser.setId(1);
////        existingUser.setEmail("testuser@example.com");
////        existingUser.setPassword(passwordEncoder.encode("password123"));
////
////        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(existingUser);
////        when(passwordEncoder.matches(loginDto.getPassword(), existingUser.getPassword())).thenReturn(true);
////
////
////        // When
////        LoginResponse mockResponse = new LoginResponse(true, "Logged in Successfully", "mockedJwtToken", "mockedRefreshToken");
////        LoginResponse response = userService.login(loginDto);
////        // Then
////        verify(userRepository, times(1)).findByEmail(loginDto.getEmail());
////        verify(passwordEncoder, times(1)).matches(loginDto.getPassword(), existingUser.getPassword());
////
////
////        assertAll(() -> {
////            assertEquals(true, moc.getBody().isFlag());
////            assertEquals("Logged in Successfully", responseEntity.getBody().getMessage());
////            assertEquals("mockedJwtToken", responseEntity.getBody().getToken());
////            assertEquals("mockedRefreshToken", responseEntity.getBody().getRefreshToken());
////        });
////    }
////
//
////    @Test
////    public void testLogin() {
////        // Prepare test data
////        LoginDto loginDto = new LoginDto();
////        loginDto.setEmail("test@example.com");
////        loginDto.setPassword("testpassword");
////
////        User user = new User();
////        user.setEmail(loginDto.getEmail());
////        user.setPassword("hashedpassword");
////
////        // Mock repository behavior
////        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(user);
////        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);
////        when(roleRepository.findByName("ROLE_USER")).thenReturn(null);
////        when(jwtService.generateToken(user)).thenReturn("mockedJWTToken");
////        when(jwtService.generateRefreshToken()).thenReturn("mockedRefreshToken");
////        when(refreshTokenRepository.findByUserId(1)).thenReturn(null);
////
////        // Call the method under test
////        LoginResponse response = userService.login(loginDto);
////
////        // Assertions
////        assertEquals(true, response.isFlag());
////        assertEquals("Logged in Successfully !!!", response.getMessage());
////        assertEquals("mockedJWTToken", response.getToken());
////        assertEquals("mockedRefreshToken", response.getRefreshToken());
////    }
//}
