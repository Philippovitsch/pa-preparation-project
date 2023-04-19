package com.codecool.tasktracker;

import com.codecool.tasktracker.endpoints.TaskEndpoint;
import com.codecool.tasktracker.repositories.TaskRepository;
import com.codecool.tasktracker.service.InitService;
import com.codecool.tasktracker.service.TaskEndpointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TaskTrackerApplicationTest {

    @Autowired
    private TaskEndpoint taskEndpoint;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskEndpointService taskEndpointService;

    @Autowired
    private InitService initService;

    @Test
    public void contextLoads() {
        assertThat(taskEndpoint).isNotNull();
        assertThat(taskRepository).isNotNull();
        assertThat(taskEndpointService).isNotNull();
        assertThat(initService).isNotNull();
    }

}
