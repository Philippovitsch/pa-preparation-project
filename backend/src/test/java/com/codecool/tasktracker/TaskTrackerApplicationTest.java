package com.codecool.tasktracker;

import com.codecool.tasktracker.endpoints.AuthEndpoint;
import com.codecool.tasktracker.endpoints.TagEndpoint;
import com.codecool.tasktracker.endpoints.TaskEndpoint;
import com.codecool.tasktracker.endpoints.UserEndpoint;
import com.codecool.tasktracker.repositories.TagRepository;
import com.codecool.tasktracker.repositories.TaskRepository;
import com.codecool.tasktracker.repositories.UserRepository;
import com.codecool.tasktracker.security.AuthContextUtil;
import com.codecool.tasktracker.security.JwtGenerator;
import com.codecool.tasktracker.security.UserDetailsServiceImpl;
import com.codecool.tasktracker.service.AuthEndpointService;
import com.codecool.tasktracker.service.TagEndpointService;
import com.codecool.tasktracker.service.TaskEndpointService;
import com.codecool.tasktracker.service.UserEndpointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TaskTrackerApplicationTest {

    @Autowired
    private AuthEndpoint authEndpoint;

    @Autowired
    private TagEndpoint tagEndpoint;

    @Autowired
    private TaskEndpoint taskEndpoint;

    @Autowired
    private UserEndpoint userEndpoint;

    @Autowired
    private AuthEndpointService authEndpointService;

    @Autowired
    private TagEndpointService tagEndpointService;

    @Autowired
    private TaskEndpointService taskEndpointService;

    @Autowired
    private UserEndpointService userEndpointService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthContextUtil authContextUtil;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void contextLoads() {
        assertThat(authEndpoint).isNotNull();
        assertThat(tagEndpoint).isNotNull();
        assertThat(taskEndpoint).isNotNull();
        assertThat(userEndpoint).isNotNull();

        assertThat(tagRepository).isNotNull();
        assertThat(taskRepository).isNotNull();
        assertThat(userRepository).isNotNull();

        assertThat(authEndpointService).isNotNull();
        assertThat(tagEndpointService).isNotNull();
        assertThat(taskEndpointService).isNotNull();
        assertThat(userEndpointService).isNotNull();

        assertThat(authContextUtil).isNotNull();
        assertThat(jwtGenerator).isNotNull();
        assertThat(userDetailsService).isNotNull();
    }

}
