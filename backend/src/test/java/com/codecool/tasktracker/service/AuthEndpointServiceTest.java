package com.codecool.tasktracker.service;

import com.codecool.tasktracker.dto.TokenDto;
import com.codecool.tasktracker.dto.UserDataDto;
import com.codecool.tasktracker.security.JwtGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthEndpointServiceTest {

    private JwtGenerator jwtGenerator;
    private InMemoryUserDetailsManager userDetailsManager;
    private PasswordEncoder encoder;
    private AuthEndpointService authEndpointService;
    private Authentication authentication;

    @BeforeEach
    public void init() {
        this.authentication = mock(Authentication.class);
        this.jwtGenerator = mock(JwtGenerator.class);
        this.userDetailsManager = mock(InMemoryUserDetailsManager.class);
        this.encoder = mock(PasswordEncoder.class);
        this.authEndpointService = new AuthEndpointService(
                jwtGenerator,
                userDetailsManager,
                encoder
        );
    }

    @Test
    public void authenticateTestSuccess() {
        when(jwtGenerator.generate(any())).thenReturn("testToken");
        when(authentication.getName()).thenReturn("user");
        TokenDto tokenDto = authEndpointService.authenticate(authentication);
        assertEquals("user", tokenDto.username());
        assertEquals("testToken", tokenDto.token());
    }

    @Test
    public void authenticateTestReturnsNull() {
        assertNull(authEndpointService.authenticate(null));
    }

    @Test
    public void signUpTestSuccess() {
        UserDataDto signUpData = new UserDataDto("user", "user");
        when(userDetailsManager.userExists(any())).thenReturn(false);
        when(encoder.encode(any())).thenReturn(new BCryptPasswordEncoder().encode(signUpData.password()));
        assertEquals(signUpData, authEndpointService.signUp(signUpData));
    }

    @Test
    public void signUpDataTestReturnsNull() {
        UserDataDto signUpData = new UserDataDto("user", "user");
        when(userDetailsManager.userExists(any())).thenReturn(true);
        assertNull(authEndpointService.signUp(signUpData));
    }

}
