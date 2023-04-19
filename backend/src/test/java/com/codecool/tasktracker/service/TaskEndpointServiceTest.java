package com.codecool.tasktracker.service;

import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskEndpointServiceTest {

    private TaskRepository taskRepository;
    private TaskEndpointService taskEndpointService;

    @BeforeEach
    public void init() {
        taskRepository = mock(TaskRepository.class);
        taskEndpointService = new TaskEndpointService(taskRepository);
    }

    @Test
    public void getAllTasksTest() {
        List<Task> tasks = List.of(
                new Task("Task 1", "Test description 1", Timestamp.valueOf("2023-04-16 02:00:00.0")),
                new Task("Task 2", "Test description 2", Timestamp.valueOf("2023-04-17 02:00:00.0")),
                new Task("Task 3", "Test description 3", Timestamp.valueOf("2023-04-18 02:00:00.0"))
        );
        when(taskRepository.findAll()).thenReturn(tasks);
        assertEquals(tasks, taskEndpointService.getAllTasks());
    }

    @Test
    public void getTaskByNameTest() {
        Task task = new Task("John's Task", "Test description", Timestamp.valueOf("2023-04-19 02:00:00.0"));
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        assertEquals(task, taskEndpointService.getTaskByName(task.getName()));
    }

    @Test
    public void saveTaskTest() {
        Task task = new Task("Phil's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"));
        when(taskRepository.save(any())).thenReturn(task);
        assertEquals(task, taskEndpointService.saveTask(task));
    }

    @Test
    public void updateTaskByNameTest() {
        Task task = new Task("El's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"));
        Task updatedTask = new Task("El's New Task", "New test description", Timestamp.valueOf("2023-04-20 02:00:00.0"));
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        when(taskRepository.save(any())).thenReturn(task);
        assertEquals(updatedTask.getName(), taskEndpointService.updateTaskByName("Phil's Task", updatedTask).getName());
        assertEquals(updatedTask.getDescription(), taskEndpointService.updateTaskByName("Phil's Task", updatedTask).getDescription());
        assertEquals(updatedTask.getTimestamp(), taskEndpointService.updateTaskByName("Phil's Task", updatedTask).getTimestamp());
    }

    @Test
    public void deleteTaskByNameTest() {
        Task task = new Task("Emad's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"));
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        doNothing().when(taskRepository).delete(any());
        taskEndpointService.deleteTaskByName(task.getName());
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(1)).delete(any());
    }

}
