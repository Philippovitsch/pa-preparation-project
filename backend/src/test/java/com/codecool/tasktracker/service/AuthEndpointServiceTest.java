package com.codecool.tasktracker.service;

import com.codecool.tasktracker.dto.AuthenticationRequestDto;
import com.codecool.tasktracker.security.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthEndpointServiceTest {

    private AuthenticationManager authenticationManager;
    private InMemoryUserDetailsManager userDetailsService;
    private JwtUtils jwtUtils;
    private PasswordEncoder encoder;
    private AuthEndpointService authEndpointService;

    @BeforeEach
    public void init() {
        this.authenticationManager = mock(AuthenticationManager.class);
        this.userDetailsService = mock(InMemoryUserDetailsManager.class);
        this.jwtUtils = mock(JwtUtils.class);
        this.encoder = mock(PasswordEncoder.class);
        this.authEndpointService = new AuthEndpointService(
                authenticationManager,
                userDetailsService,
                jwtUtils,
                encoder
        );
    }

    @Test
    public void authenticateTestReturnsToken() {
        AuthenticationRequestDto signUpData = new AuthenticationRequestDto("user", "user");
        UserDetails user = User.builder()
                .username("user")
                .password(new BCryptPasswordEncoder().encode("user"))
                .roles("USER")
                .build();
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userDetailsService.loadUserByUsername(any())).thenReturn(user);
        String token = authEndpointService.authenticate(signUpData);
        assertEquals(jwtUtils.generateToken(user), token);
    }

    @Test
    public void authenticateTestReturnsNull() {
        AuthenticationRequestDto signUpData = new AuthenticationRequestDto("user", "user");
        UserDetails user = User.builder()
                .username("user")
                .password(new BCryptPasswordEncoder().encode("user"))
                .roles("USER")
                .build();
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userDetailsService.loadUserByUsername(any())).thenReturn(null);
        assertNull(authEndpointService.authenticate(signUpData));
    }

    @Test
    public void signUpTestReturnsTrue() {
        AuthenticationRequestDto signUpData = new AuthenticationRequestDto("user", "user");
        when(userDetailsService.userExists(any())).thenReturn(false);
        when(encoder.encode(any())).thenReturn(new BCryptPasswordEncoder().encode(signUpData.getPassword()));
        assertTrue(authEndpointService.signUp(signUpData));
    }

    @Test
    public void signUpDataTestReturnsFalse() {
        AuthenticationRequestDto signUpData = new AuthenticationRequestDto("user", "user");
        when(userDetailsService.userExists(any())).thenReturn(true);
        assertFalse(authEndpointService.signUp(signUpData));
    }

}
