package com.codecool.tasktracker.service;

import com.codecool.tasktracker.dto.UserDetailsDto;
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
        UserDetails user =  userDetailsService.loadUserByUsername(username);
        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new UserDetailsDto(
                user.getUsername(),
                authorities,
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                user.isEnabled()
        );
    }
}
