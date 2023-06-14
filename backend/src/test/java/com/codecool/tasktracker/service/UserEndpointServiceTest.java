package com.codecool.tasktracker.service;

import com.codecool.tasktracker.model.User;
import com.codecool.tasktracker.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserEndpointServiceTest {

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private UserEndpointService userEndpointService;

    @Test
    public void getUserDataTest() {
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setAuthorities(Set.of("TEST_ROLE"));

        when(userDetailsService.getUserByUsername(any())).thenReturn(user);
        assertEquals(user.getUsername(), userEndpointService.getUserData("testuser").username());
    }

    @Test
    public void getUserDataReturnsNull() {
        when(userDetailsService.getUserByUsername(any())).thenReturn(null);
        assertNull(userEndpointService.getUserData("testuser"));
    }

}
