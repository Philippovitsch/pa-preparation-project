package com.codecool.tasktracker.service;

import com.codecool.tasktracker.dto.AuthenticationRequestDto;
import com.codecool.tasktracker.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AuthEndpointService {

    private final AuthenticationManager authenticationManager;
    private final InMemoryUserDetailsManager userDetailsService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;

    public AuthEndpointService(AuthenticationManager authenticationManager, InMemoryUserDetailsManager userDetailsService, JwtUtils jwtUtils, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
    }

    public String authenticate(AuthenticationRequestDto request) {
        String username = request.getUsername();
        String password = request.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        final UserDetails user = userDetailsService.loadUserByUsername(username);
        if (user != null) {
            return jwtUtils.generateToken(user);
        }
        return null;
    }

    public boolean signUp(AuthenticationRequestDto request) {
        if (userDetailsService.userExists(request.getUsername())) {
            return false;
        }

        UserDetails user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .roles("USER")
                .build();
        userDetailsService.createUser(user);

        return true;
    }

}
