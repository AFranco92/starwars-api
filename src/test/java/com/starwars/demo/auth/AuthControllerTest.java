package com.starwars.demo.auth;

import com.starwars.demo.security.controller.AuthController;
import com.starwars.demo.security.jwt.JwtUtil;
import com.starwars.demo.security.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @Test
    void testLoginSuccess() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("user123");

        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtUtil.generateToken("user")).thenReturn("mocked-jwt-token");

        ResponseEntity<?> response = authController.login(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(Map.class, response.getBody());
        assertEquals("mocked-jwt-token", ((Map<?, ?>) response.getBody()).get("token"));
    }

    @Test
    void testLoginInvalidCredentials() {
        User user = new User();
        user.setUsername("wrong");
        user.setPassword("wrongpass");

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid"));

        ResponseEntity<?> response = authController.login(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

}