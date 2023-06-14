package com.codecool.tasktracker.security;

import com.codecool.tasktracker.model.User;
import com.codecool.tasktracker.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user;

    @BeforeEach
    public void initUser() {
        user = new User();
        user.setUsername("testuser");
    }

    @Test
    public void loadUserByUsernameSuccessTest() {
        when(userRepository.findUserByUsername(any())).thenReturn(user);
        assertEquals(user.getUsername(), userDetailsService.loadUserByUsername("testuser").getUsername());
    }

    @Test
    public void loadUserByUsernameThrowsError() {
        when(userRepository.findUserByUsername(any())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("testuser"));
    }

    @Test
    public void getUserByUsernameTest() {
        when(userRepository.findUserByUsername(any())).thenReturn(user);
        assertEquals(user, userDetailsService.getUserByUsername("testuser"));
    }

    @Test
    public void createUserTest() {
        when(userRepository.save(any())).thenReturn(user);
        assertEquals(user, userDetailsService.createUser(user));
    }

    @Test
    public void userExistsReturnsTrue() {
        when(userRepository.existsByUsername(any())).thenReturn(true);
        assertTrue(userDetailsService.userExists("testuser"));
    }

    @Test
    public void userExistsReturnsFalse() {
        when(userRepository.existsByUsername(any())).thenReturn(false);
        assertFalse(userDetailsService.userExists("testuser"));
    }

}
