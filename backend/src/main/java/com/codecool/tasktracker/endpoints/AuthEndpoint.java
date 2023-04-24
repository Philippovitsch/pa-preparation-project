package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.dto.AuthenticationRequestDto;
import com.codecool.tasktracker.exceptions.UserAlreadyExistsError;
import com.codecool.tasktracker.service.AuthEndpointService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthEndpoint {

    private final AuthEndpointService authEndpointService;

    public AuthEndpoint(AuthEndpointService authEndpointService) {
        this.authEndpointService = authEndpointService;
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthenticationRequestDto request) {
        String token = authEndpointService.authenticate(request);
        if (token == null) throw new UsernameNotFoundException("User not found!");
        return token;
    }

    @PostMapping("/sign-up")
    public AuthenticationRequestDto signUp(@RequestBody AuthenticationRequestDto request) throws UserAlreadyExistsError {
        boolean isSignedUp = authEndpointService.signUp(request);
        if (!isSignedUp) throw new UserAlreadyExistsError("User already exists!");
        return request;
    }

}
