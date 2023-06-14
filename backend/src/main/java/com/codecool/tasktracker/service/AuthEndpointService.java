package com.codecool.tasktracker.service;

import com.codecool.tasktracker.dto.TokenDto;
import com.codecool.tasktracker.dto.UserDataDto;
import com.codecool.tasktracker.model.User;
import com.codecool.tasktracker.security.JwtGenerator;
import com.codecool.tasktracker.security.UserDetailsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthEndpointService {

    private final JwtGenerator jwtGenerator;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder encoder;

    public AuthEndpointService(JwtGenerator jwtGenerator, UserDetailsServiceImpl userDetailsService, PasswordEncoder encoder) {
        this.jwtGenerator = jwtGenerator;
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    public TokenDto authenticate(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        String username = authentication.getName();
        Set<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        String token = jwtGenerator.generate(authentication);
        return new TokenDto(username, roles, token);
    }

    public User signUp(UserDataDto userDataDto) {
        if (userDetailsService.userExists(userDataDto.username())) {
            return null;
        }

        User user = new User();
        user.setUsername(userDataDto.username());
        user.setPassword(encoder.encode(userDataDto.password()));
        user.setAuthorities(Set.of("USER"));
        return userDetailsService.createUser(user);
    }

}
