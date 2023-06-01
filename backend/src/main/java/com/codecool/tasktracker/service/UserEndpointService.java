package com.codecool.tasktracker.service;

import com.codecool.tasktracker.dto.UserDetailsDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserEndpointService {

    private final InMemoryUserDetailsManager userDetails;

    public UserEndpointService(InMemoryUserDetailsManager userDetails) {
        this.userDetails = userDetails;
    }

    public UserDetailsDto getUserData(String username) {
        UserDetails user =  userDetails.loadUserByUsername(username);
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
