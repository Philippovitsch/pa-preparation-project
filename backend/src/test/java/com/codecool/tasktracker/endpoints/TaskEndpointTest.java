package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.dto.TaskDto;
import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.model.User;
import com.codecool.tasktracker.service.TaskEndpointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskEndpoint.class)
public class TaskEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskEndpointService taskEndpointService;

    @Test
    @WithMockUser
    public void getAllTasksTest() throws Exception {
        List<Task> tasks = List.of(
                new Task(1L, new User(), "Task 1", "Test description 2", Timestamp.valueOf("2023-04-16 02:00:00.0"), new HashSet<>()),
                new Task(2L, new User(), "Task 2", "Test description 2", Timestamp.valueOf("2023-04-17 02:00:00.0"), new HashSet<>()),
                new Task(3L, new User(), "Task 3", "Test description 3", Timestamp.valueOf("2023-04-18 02:00:00.0"), new HashSet<>())
        );
        when(taskEndpointService.getAllTasks()).thenReturn(tasks);
        mockMvc.perform(get("/api/tasks/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[0].name", is(tasks.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(tasks.get(1).getName())))
                .andExpect(jsonPath("$[2].name", is(tasks.get(2).getName())));
    }

    @Test
    @WithMockUser
    public void getTasksByUsernameTest() throws Exception {
        List<Task> tasks = List.of(
                new Task(1L, new User(), "Task 1", "Test description 2", Timestamp.valueOf("2023-04-16 02:00:00.0"), new HashSet<>()),
                new Task(2L, new User(), "Task 2", "Test description 2", Timestamp.valueOf("2023-04-17 02:00:00.0"), new HashSet<>()),
                new Task(3L, new User(), "Task 3", "Test description 3", Timestamp.valueOf("2023-04-18 02:00:00.0"), new HashSet<>())
        );
        when(taskEndpointService.getTasksByUsername(any())).thenReturn(tasks);
        mockMvc.perform(get("/api/tasks/user/username"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[0].name", is(tasks.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(tasks.get(1).getName())))
                .andExpect(jsonPath("$[2].name", is(tasks.get(2).getName())));
    }

    @Test
    @WithMockUser
    public void getTaskByNameTest() throws Exception {
        Task task = new Task(1L, new User(), "John's Task", "Test description", Timestamp.valueOf("2023-04-19 02:00:00.0"), new HashSet<>());
        when(taskEndpointService.getTaskByName(any())).thenReturn(task);
        mockMvc.perform(get("/api/tasks/John"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.timestamp", startsWith(task.getTimestamp().toString().substring(0, 10))));
    }

    @Test
    @WithMockUser
    public void getTaskByNameThrowsError() throws Exception {
        when(taskEndpointService.getTaskByName(any())).thenReturn(null);
        mockMvc.perform(get("/api/tasks/John"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @WithMockUser
    public void saveTaskTest() throws Exception {
        TaskDto taskDto = new TaskDto("user", "Phil's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        Task newTask = new Task(1L, new User(), "Phil's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        when(taskEndpointService.saveTask(any())).thenReturn(newTask);
        mockMvc.perform(post("/api/tasks").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(newTask.getName())))
                .andExpect(jsonPath("$.description", is(newTask.getDescription())))
                .andExpect(jsonPath("$.timestamp", startsWith(newTask.getTimestamp().toString().substring(0, 10))));
    }

    @Test
    @WithMockUser
    public void saveTaskThrowsError() throws Exception {
        TaskDto taskDto = new TaskDto("user", "Phil's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"), new HashSet<>());
        when(taskEndpointService.saveTask(any())).thenReturn(null);
        mockMvc.perform(post("/api/tasks").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.CONFLICT.value()));
    }

    @Test
    @WithMockUser
    public void updateTaskByNameTest() throws Exception {
        TaskDto taskDto = new TaskDto("user", "El's Task", "Test description", Timestamp.valueOf("2023-04-21 02:00:00.0"), new HashSet<>());
        Task newTask = new Task(1L, new User(), "El's Task", "Test description", Timestamp.valueOf("2023-04-21 02:00:00.0"), new HashSet<>());
        when(taskEndpointService.updateTaskByName(any(), any())).thenReturn(newTask);
        mockMvc.perform(put("/api/tasks/El").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(newTask.getName())))
                .andExpect(jsonPath("$.description", is(newTask.getDescription())))
                .andExpect(jsonPath("$.timestamp", startsWith(newTask.getTimestamp().toString().substring(0, 10))));
    }

    @Test
    @WithMockUser
    public void updateTaskByNameThrowsError() throws Exception {
        TaskDto taskDto = new TaskDto("user", "El's Task", "Test description", Timestamp.valueOf("2023-04-21 02:00:00.0"), new HashSet<>());
        when(taskEndpointService.updateTaskByName(any(), any())).thenReturn(null);
        mockMvc.perform(put("/api/tasks/El").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @WithMockUser
    public void deleteTaskByNameTest() throws Exception {
        Task task = new Task(1L, new User(), "Emad's Task", "Test description", Timestamp.valueOf("2023-04-22 02:00:00.0"), new HashSet<>());
        when(taskEndpointService.deleteTaskByName(any())).thenReturn(task);
        mockMvc.perform(delete("/api/tasks/Emad").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.timestamp", startsWith(task.getTimestamp().toString().substring(0, 10))));
    }

    @Test
    @WithMockUser
    public void deleteTaskByNameThrowsError() throws Exception {
        when(taskEndpointService.deleteTaskByName(any())).thenReturn(null);
        mockMvc.perform(delete("/api/tasks/Emad").with(csrf()))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

}
