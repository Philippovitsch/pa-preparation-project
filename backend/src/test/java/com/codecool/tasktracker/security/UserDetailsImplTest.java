package com.codecool.tasktracker.security;

import com.codecool.tasktracker.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserDetailsImplTest {

    @Test
    public void testAllGetters() {
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setAuthorities(Set.of("TEST_ROLE"));

        UserDetails userDetails = new UserDetailsImpl(user);
        Set<String> userDetailsAuthorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        assertEquals(user.getAuthorities(), userDetailsAuthorities);
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

}
