package com.codecool.tasktracker.service;

import com.codecool.tasktracker.dto.TokenDto;
import com.codecool.tasktracker.dto.UserDataDto;
import com.codecool.tasktracker.security.JwtGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AuthEndpointService {

    private final JwtGenerator jwtGenerator;
    private final InMemoryUserDetailsManager userDetailsManager;
    private final PasswordEncoder encoder;

    public AuthEndpointService(JwtGenerator jwtGenerator, InMemoryUserDetailsManager userDetailsManager, PasswordEncoder encoder) {
        this.jwtGenerator = jwtGenerator;
        this.userDetailsManager = userDetailsManager;
        this.encoder = encoder;
    }

    public TokenDto authenticate(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        String username = authentication.getName();
        String token = jwtGenerator.generate(authentication);
        return new TokenDto(username, token);
    }

    public UserDataDto signUp(UserDataDto userDataDto) {
        if (userDetailsManager.userExists(userDataDto.username())) {
            return null;
        }

        UserDetails user = User.builder()
                .username(userDataDto.username())
                .password(encoder.encode(userDataDto.password()))
                .roles("USER")
                .build();
        userDetailsManager.createUser(user);
        return userDataDto;
    }

}
