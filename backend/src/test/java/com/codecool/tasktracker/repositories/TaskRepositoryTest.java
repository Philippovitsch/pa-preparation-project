package com.codecool.tasktracker.repositories;

import com.codecool.tasktracker.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void checkForEmptyDatabase() {
        List<Task> tasks = taskRepository.findAll();
        assertThat(tasks).isEmpty();
    }

    @Test
    public void getTaskByNameTest() {
        Task task1 = new Task("Task 1", "Test description 1", Timestamp.valueOf("2023-04-16 02:00:00.0"));
        Task task2 = new Task("Task 2", "Test description 2", Timestamp.valueOf("2023-04-17 02:00:00.0"));
        Task task3 = new Task("Task 3", "Test description 3", Timestamp.valueOf("2023-04-18 02:00:00.0"));
        entityManager.persist(task1);
        entityManager.persist(task2);
        entityManager.persist(task3);
        Task returnedTask = taskRepository.getTaskByName("Task 1");
        assertEquals(task1, returnedTask);
    }

}
