package com.travelPlus.UnitTests;

import com.travelPlus.v1.DTO.AuthDTO;
import com.travelPlus.v1.DTO.JWTResponse;
import com.travelPlus.v1.Entity.User;
import com.travelPlus.v1.Repo.UserRepo;
import com.travelPlus.v1.Service.AuthService;
import com.travelPlus.v1.Service.JwtService;
import com.travelPlus.v1.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Mock
    private UserRepo userRepository;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;


    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @Order(2)
    public void testSignin() {
        // Mock data
        String email = "test@example.com";
        String password = "password";

        User user = new User();
        user.setUserId(1L);
        user.setEmail(email);
        user.setPassword(password);

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findUserByEmail(anyString())).thenReturn(java.util.Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwt_token");

        // Call the service method
        JWTResponse result = authService.signin(new AuthDTO(email, password));

        // Assert the result
        assertEquals("jwt_token", result.getToken());
    }
}
