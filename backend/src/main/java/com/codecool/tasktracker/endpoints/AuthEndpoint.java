package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.dto.TokenDto;
import com.codecool.tasktracker.dto.UserDataDto;
import com.codecool.tasktracker.exceptions.AuthenticationException;
import com.codecool.tasktracker.exceptions.UserAlreadyExistsError;
import com.codecool.tasktracker.model.User;
import com.codecool.tasktracker.service.AuthEndpointService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthEndpoint {

    private final AuthEndpointService authEndpointService;

    public AuthEndpoint(AuthEndpointService authEndpointService) {
        this.authEndpointService = authEndpointService;
    }

    @PostMapping("/authenticate")
    public TokenDto authenticate(Authentication authentication) {
        TokenDto tokenDto = authEndpointService.authenticate(authentication);
        if (tokenDto == null) throw new AuthenticationException("Authentication not provided!");
        return tokenDto;
    }

    @PostMapping("/sign-up")
    public User signUp(@RequestBody UserDataDto loginData) {
        User user = authEndpointService.signUp(loginData);
        if (user == null) throw new UserAlreadyExistsError("User already exists!");
        return user;
    }

}
