package com.codecool.tasktracker.repositories;

import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    public void deleteDatabaseEntries() {
        taskRepository.deleteAll();
    }

    @Test
    public void checkForEmptyDatabase() {
        List<Task> tasks = taskRepository.findAll();
        assertThat(tasks).isEmpty();
    }

    @Test
    public void getTaskByNameTest() {
        Task task1 = new Task();
        Task task2 = new Task();
        task1.setName("Task1");
        task2.setName("Task2");
        entityManager.persist(task1);
        entityManager.persist(task2);

        assertEquals(task1, taskRepository.getTaskByName(task1.getName()));
        assertEquals(task2, taskRepository.getTaskByName(task2.getName()));
    }

    @Test
    public void getTaskByUserIdTest() {
        User user = new User();
        user.setId(1);

        Task task1 = new Task();
        task1.setUser(user);

        Task task2 = new Task();
        task2.setUser(user);

        entityManager.persist(task1);
        entityManager.persist(task2);

        assertEquals(Arrays.asList(task1, task2), taskRepository.getTaskByUserId(1));
        assertEquals(0, taskRepository.getTaskByUserId(2).size());
    }

}
