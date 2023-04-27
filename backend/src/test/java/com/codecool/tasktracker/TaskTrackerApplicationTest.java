package com.codecool.tasktracker;

import com.codecool.tasktracker.endpoints.AuthEndpoint;
import com.codecool.tasktracker.endpoints.TagEndpoint;
import com.codecool.tasktracker.endpoints.TaskEndpoint;
import com.codecool.tasktracker.repositories.TagRepository;
import com.codecool.tasktracker.repositories.TaskRepository;
import com.codecool.tasktracker.service.AuthEndpointService;
import com.codecool.tasktracker.service.TagEndpointService;
import com.codecool.tasktracker.service.TaskEndpointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TaskTrackerApplicationTest {

    @Autowired
    private AuthEndpoint authEndpoint;

    @Autowired
    private AuthEndpointService authEndpointService;

    @Autowired
    private TagEndpoint tagEndpoint;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagEndpointService tagEndpointService;

    @Autowired
    private TaskEndpoint taskEndpoint;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskEndpointService taskEndpointService;

    @Test
    public void contextLoads() {
        assertThat(authEndpoint).isNotNull();
        assertThat(authEndpointService).isNotNull();
        assertThat(tagEndpoint).isNotNull();
        assertThat(tagRepository).isNotNull();
        assertThat(tagEndpointService).isNotNull();
        assertThat(taskEndpoint).isNotNull();
        assertThat(taskRepository).isNotNull();
        assertThat(taskEndpointService).isNotNull();
    }

}
