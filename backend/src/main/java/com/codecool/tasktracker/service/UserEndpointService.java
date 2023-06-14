package com.codecool.tasktracker.service;

import com.codecool.tasktracker.dto.UserDetailsDto;
import com.codecool.tasktracker.model.User;
import com.codecool.tasktracker.security.UserDetailsImpl;
import com.codecool.tasktracker.security.UserDetailsServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserEndpointService {

    private final UserDetailsServiceImpl userDetailsService;

    public UserEndpointService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserDetailsDto getUserData(String username) {
        User user =  userDetailsService.getUserByUsername(username);

        if (user == null) {
            return null;
        }

        UserDetails userDetails = new UserDetailsImpl(user);
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new UserDetailsDto(
                userDetails.getUsername(),
                authorities,
                userDetails.isAccountNonExpired(),
                userDetails.isAccountNonLocked(),
                userDetails.isCredentialsNonExpired(),
                userDetails.isEnabled()
        );
    }
}
