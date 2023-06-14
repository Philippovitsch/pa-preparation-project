package com.codecool.tasktracker.service;

import com.codecool.tasktracker.dto.TaskDto;
import com.codecool.tasktracker.exceptions.AuthenticationException;
import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.model.User;
import com.codecool.tasktracker.repositories.TaskRepository;
import com.codecool.tasktracker.repositories.UserRepository;
import com.codecool.tasktracker.security.AuthContextUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskEndpointServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthContextUtil authContextUtil;

    @InjectMocks
    private TaskEndpointService taskEndpointService;

    @Test
    public void getAllTasksTest() {
        List<Task> tasks = List.of(
                new Task(1L, new User(), "Task 1", "Test description 2", Timestamp.valueOf("2023-04-16 02:00:00.0"), new HashSet<>()),
                new Task(2L, new User(), "Task 2", "Test description 2", Timestamp.valueOf("2023-04-17 02:00:00.0"), new HashSet<>()),
                new Task(3L, new User(), "Task 3", "Test description 3", Timestamp.valueOf("2023-04-18 02:00:00.0"), new HashSet<>())
        );
        when(taskRepository.findAll()).thenReturn(tasks);
        assertEquals(tasks, taskEndpointService.getAllTasks());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void getTaskByUsernameTest() {
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        List<Task> tasks = List.of(
                new Task(1L, new User(), "Task 1", "Test description 2", Timestamp.valueOf("2023-04-16 02:00:00.0"), new HashSet<>()),
                new Task(2L, new User(), "Task 2", "Test description 2", Timestamp.valueOf("2023-04-17 02:00:00.0"), new HashSet<>()),
                new Task(3L, new User(), "Task 3", "Test description 3", Timestamp.valueOf("2023-04-18 02:00:00.0"), new HashSet<>())
        );
        when(userRepository.findUserByUsername(any())).thenReturn(user);
        when(taskRepository.getTaskByUserId(anyLong())).thenReturn(tasks);
        assertEquals(tasks, taskEndpointService.getTasksByUsername("testuser"));
    }

    @Test
    public void getTaskByUsernameReturnsEmptyList() {
        when(userRepository.findUserByUsername(any())).thenReturn(null);
        assertEquals(0, taskEndpointService.getTasksByUsername("testuser").size());
    }

    @Test
    public void getTaskByNameTest() {
        Task task = new Task(1L, new User(), "John's Task", "Test description", Timestamp.valueOf("2023-04-19 02:00:00.0"), new HashSet<>());
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        assertEquals(task, taskEndpointService.getTaskByName(task.getName()));
        verify(taskRepository, times(1)).getTaskByName(any());
    }

    @Test
    public void getTaskByNameReturnsNull() {
        Task task = new Task(1L, new User(), "John's Task", "Test description", Timestamp.valueOf("2023-04-19 02:00:00.0"), new HashSet<>());
        when(taskRepository.getTaskByName(any())).thenReturn(null);
        assertNull(taskEndpointService.getTaskByName(task.getName()));
        verify(taskRepository, times(1)).getTaskByName(any());
    }

    @Test
    public void saveTaskTest() {
        TaskDto taskDto = new TaskDto("user", "Phil's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        Task task = new Task(1L, new User(), "Phil's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        when(userRepository.findUserByUsername(any())).thenReturn(new User());
        when(taskRepository.getTaskByName(any())).thenReturn(null);
        when(taskRepository.save(any())).thenReturn(task);
        assertEquals(task, taskEndpointService.saveTask(taskDto));
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    public void saveTaskReturnsNull() {
        TaskDto taskDto = new TaskDto("user", "Phil's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        Task task = new Task(1L, new User(), "Phil's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        when(userRepository.findUserByUsername(any())).thenReturn(new User());
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        assertNull(taskEndpointService.saveTask(taskDto));
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(0)).save(any());
    }

    @Test
    public void updateTaskByNameTest() {
        Task task = new Task(1L, new User(), "El's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        TaskDto updatedTask = new TaskDto("user", "El's New Task", "New test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        when(authContextUtil.isTaskOwnerOrAdmin(any())).thenReturn(true);
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        when(taskRepository.save(any())).thenReturn(task);
        assertEquals(updatedTask.name(), taskEndpointService.updateTaskByName("Phil's Task", updatedTask).getName());
        assertEquals(updatedTask.description(), taskEndpointService.updateTaskByName("Phil's Task", updatedTask).getDescription());
        assertEquals(updatedTask.timestamp(), taskEndpointService.updateTaskByName("Phil's Task", updatedTask).getTimestamp());
        verify(taskRepository, times(3)).getTaskByName(any());
        verify(taskRepository, times(3)).save(any());
        verify(authContextUtil, times(3)).isTaskOwnerOrAdmin(any());
    }

    @Test
    public void updateTaskByNameReturnsNull() {
        TaskDto updatedTask = new TaskDto("user", "El's New Task", "New test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        when(taskRepository.getTaskByName(any())).thenReturn(null);
        assertNull(taskEndpointService.updateTaskByName("Phil's Task", updatedTask));
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(0)).save(any());
        verify(authContextUtil, times(0)).isTaskOwnerOrAdmin(any());
    }

    @Test
    public void updateTaskByNameThrowsError() {
        Task task = new Task(1L, new User(), "El's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        TaskDto updatedTask = new TaskDto("user", "El's New Task", "New test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        when(authContextUtil.isTaskOwnerOrAdmin(any())).thenReturn(false);
        assertThrows(AuthenticationException.class, () -> taskEndpointService.updateTaskByName("Phil's Task", updatedTask));
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(0)).save(any());
        verify(authContextUtil, times(1)).isTaskOwnerOrAdmin(any());
    }


    @Test
    public void deleteTaskByNameTest() {
        Task task = new Task(1L, new User(), "Emad's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        when(authContextUtil.isTaskOwnerOrAdmin(any())).thenReturn(true);
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        doNothing().when(taskRepository).delete(any());
        assertEquals(task, taskEndpointService.deleteTaskByName(task.getName()));
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(1)).delete(any());
        verify(authContextUtil, times(1)).isTaskOwnerOrAdmin(any());

    }

    @Test
    public void deleteTaskByNameReturnsNull() {
        Task task = new Task(1L, new User(), "Emad's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        when(taskRepository.getTaskByName(any())).thenReturn(null);
        assertNull(taskEndpointService.deleteTaskByName(task.getName()));
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(0)).delete(any());
        verify(authContextUtil, times(0)).isTaskOwnerOrAdmin(any());

    }

    @Test
    public void deleteTaskByNameThrowsError() {
        Task task = new Task(1L, new User(), "Emad's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        when(authContextUtil.isTaskOwnerOrAdmin(any())).thenReturn(false);
        assertThrows(AuthenticationException.class, () -> taskEndpointService.deleteTaskByName(task.getName()));
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(0)).delete(any());
        verify(authContextUtil, times(1)).isTaskOwnerOrAdmin(any());
    }

}
