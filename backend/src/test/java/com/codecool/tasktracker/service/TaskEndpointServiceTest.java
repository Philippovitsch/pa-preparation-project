package com.codecool.tasktracker.service;

import com.codecool.tasktracker.dto.TaskDto;
import com.codecool.tasktracker.exceptions.AuthenticationException;
import com.codecool.tasktracker.model.Tag;
import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.model.User;
import com.codecool.tasktracker.repositories.TagRepository;
import com.codecool.tasktracker.repositories.TaskRepository;
import com.codecool.tasktracker.repositories.UserRepository;
import com.codecool.tasktracker.security.AuthContextUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TaskEndpointService taskEndpointService;

    private final Task task = Task.builder().name("Test task").description("Test description").build();
    private final List<Task> tasks = List.of(
            Task.builder().name("Task 1").build(),
            Task.builder().name("Task 2").build(),
            Task.builder().name("Task 3").build()
    );
    private final TaskDto taskDto = new TaskDto(
            "user",
            "Test Task",
            "Test description",
            Timestamp.valueOf("2023-04-20 02:00:00.0"),
            new HashSet<>(),
            Optional.empty(),
            true
    );


    @Test
    public void getAllTasksTest() {
        when(taskRepository.findAll()).thenReturn(tasks);
        assertEquals(tasks, taskEndpointService.getAllTasks());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void getTaskByUsernameTest() {
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
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
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        assertEquals(task, taskEndpointService.getTaskByName(task.getName()));
        verify(taskRepository, times(1)).getTaskByName(any());
    }

    @Test
    public void getTaskByNameReturnsNull() {
        when(taskRepository.getTaskByName(any())).thenReturn(null);
        assertNull(taskEndpointService.getTaskByName(task.getName()));
        verify(taskRepository, times(1)).getTaskByName(any());
    }

    @Test
    public void saveTaskTest() throws IOException {
        when(userRepository.findUserByUsername(any())).thenReturn(new User());
        when(taskRepository.getTaskByName(any())).thenReturn(null);
        when(taskRepository.save(any())).thenReturn(task);
        lenient().when(tagRepository.getTagByName(any())).thenReturn(new Tag());
        assertEquals(task, taskEndpointService.saveTask(taskDto));
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    public void saveTaskReturnsNull() throws IOException {
        Task task = Task.builder().name("Phil's Task").description("Test description").build();
        when(userRepository.findUserByUsername(any())).thenReturn(new User());
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        assertNull(taskEndpointService.saveTask(taskDto));
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(0)).save(any());
    }

    @Test
    public void updateTaskByNameTest() throws IOException {
        when(authContextUtil.isTaskOwnerOrAdmin(any())).thenReturn(true);
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        when(taskRepository.save(any())).thenReturn(task);
        lenient().when(tagRepository.getTagByName(any())).thenReturn(new Tag());
        assertEquals(taskDto.name(), taskEndpointService.updateTaskByName("Phil's Task", taskDto).getName());
        assertEquals(taskDto.description(), taskEndpointService.updateTaskByName("Phil's Task", taskDto).getDescription());
        verify(taskRepository, times(2)).getTaskByName(any());
        verify(taskRepository, times(2)).save(any());
        verify(authContextUtil, times(2)).isTaskOwnerOrAdmin(any());
    }

    @Test
    public void updateTaskByNameReturnsNull() throws IOException {
        when(taskRepository.getTaskByName(any())).thenReturn(null);
        assertNull(taskEndpointService.updateTaskByName("Phil's Task", taskDto));
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(0)).save(any());
        verify(authContextUtil, times(0)).isTaskOwnerOrAdmin(any());
    }

    @Test
    public void updateTaskByNameThrowsError() {
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        lenient().when(tagRepository.getTagByName(any())).thenReturn(new Tag());
        when(authContextUtil.isTaskOwnerOrAdmin(any())).thenReturn(false);
        assertThrows(AuthenticationException.class, () -> taskEndpointService.updateTaskByName("Phil's Task", taskDto));
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(0)).save(any());
        verify(authContextUtil, times(1)).isTaskOwnerOrAdmin(any());
    }

    @Test
    public void toggleIsDoneTest() {
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        when(taskRepository.save(any())).thenReturn(task);
        assertEquals(task, taskEndpointService.toggleIsDone(task.getName(), false));
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    public void toggleIsDoneTestReturnsNull() {
        when(taskRepository.getTaskByName(any())).thenReturn(null);
        assertNull(taskEndpointService.toggleIsDone(task.getName(), false));
        verify(taskRepository, times(1)).getTaskByName(any());
    }

    @Test
    public void deleteTaskByNameTest() {
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
        when(taskRepository.getTaskByName(any())).thenReturn(null);
        assertNull(taskEndpointService.deleteTaskByName(task.getName()));
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(0)).delete(any());
        verify(authContextUtil, times(0)).isTaskOwnerOrAdmin(any());

    }

    @Test
    public void deleteTaskByNameThrowsError() {
        when(taskRepository.getTaskByName(any())).thenReturn(task);
        when(authContextUtil.isTaskOwnerOrAdmin(any())).thenReturn(false);
        assertThrows(AuthenticationException.class, () -> taskEndpointService.deleteTaskByName(task.getName()));
        verify(taskRepository, times(1)).getTaskByName(any());
        verify(taskRepository, times(0)).delete(any());
        verify(authContextUtil, times(1)).isTaskOwnerOrAdmin(any());
    }

}
