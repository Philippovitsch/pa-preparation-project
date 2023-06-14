package com.codecool.tasktracker.repositories;

import com.codecool.tasktracker.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void init() {
        if (user == null) {
            user = new User();
            user.setUsername("testuser");
            entityManager.persist(user);
        }
    }

    @Test
    public void findUserByUsernameTest() {
        assertEquals(user, userRepository.findUserByUsername("testuser"));
        assertNull(userRepository.findUserByUsername("unknown"));
    }

    @Test
    public void userExistsTest() {
        assertTrue(userRepository.existsByUsername("testuser"));
        assertFalse(userRepository.existsByUsername("unknown"));
    }

}
