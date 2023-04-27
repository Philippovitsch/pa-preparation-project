package com.codecool.tasktracker.service;

import com.codecool.tasktracker.dto.TokenDto;
import com.codecool.tasktracker.dto.UserDataDto;
import com.codecool.tasktracker.security.JwtGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthEndpointServiceTest {

    @Mock
    private JwtGenerator jwtGenerator;

    @Mock
    private InMemoryUserDetailsManager userDetailsManager;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthEndpointService authEndpointService;

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
