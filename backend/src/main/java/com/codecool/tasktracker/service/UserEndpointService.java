package com.codecool.tasktracker.service;

import com.codecool.tasktracker.dto.UserDetailsDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserEndpointService {

    private final InMemoryUserDetailsManager userDetails;

    public UserEndpointService(InMemoryUserDetailsManager userDetails) {
        this.userDetails = userDetails;
    }

    public UserDetailsDto getUserData(String username) {
        UserDetails user =  userDetails.loadUserByUsername(username);
        return new UserDetailsDto(
                user.getUsername(),
                user.getAuthorities(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                user.isEnabled()
        );
    }
}
