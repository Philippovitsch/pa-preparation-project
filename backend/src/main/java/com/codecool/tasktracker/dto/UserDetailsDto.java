package com.codecool.tasktracker.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record UserDetailsDto(
        String username,
        Collection<? extends GrantedAuthority> authorities,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        boolean enabled
) {}
