package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.security.SecurityConfig;
import com.codecool.tasktracker.service.TaskEndpointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskEndpoint.class)
@Import(SecurityConfig.class)
public class TaskEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskEndpointService taskEndpointService;

    @Test
    public void userLoginSuccessTest() throws Exception {
        when(taskEndpointService.getAllTasks()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/tasks/all").with(httpBasic("user", "user")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void userLoginWrongPasswordThrowsError() throws Exception {
        when(taskEndpointService.getAllTasks()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/tasks/all").with(httpBasic("user", "wrong-password")))
                .andDo(print())
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    public void userLoginWrongUsernameThrowsError() throws Exception {
        when(taskEndpointService.getAllTasks()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/tasks/all").with(httpBasic("wrong-username", "user")))
                .andDo(print())
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    public void getAllTasksTest() throws Exception {
        List<Task> tasks = List.of(
                new Task("Task 1", "Test description 1", Timestamp.valueOf("2023-04-16 02:00:00.0")),
                new Task("Task 2", "Test description 2", Timestamp.valueOf("2023-04-17 02:00:00.0")),
                new Task("Task 3", "Test description 3", Timestamp.valueOf("2023-04-18 02:00:00.0"))
        );
        when(taskEndpointService.getAllTasks()).thenReturn(tasks);
        mockMvc.perform(get("/api/tasks/all").with(httpBasic("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[0].name", is(tasks.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(tasks.get(1).getName())))
                .andExpect(jsonPath("$[2].name", is(tasks.get(2).getName())));
    }

    @Test
    public void getTaskByNameTest() throws Exception {
        Task task = new Task("John's Task", "Test description", Timestamp.valueOf("2023-04-19 02:00:00.0"));
        when(taskEndpointService.getTaskByName(any())).thenReturn(task);
        mockMvc.perform(get("/api/tasks/John").with(httpBasic("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.timestamp", startsWith(task.getTimestamp().toString().substring(0, 10))));
    }

    @Test
    public void getTaskByNameThrowsError() throws Exception {
        when(taskEndpointService.getTaskByName(any())).thenReturn(null);
        mockMvc.perform(get("/api/tasks/John").with(httpBasic("user", "user")))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void saveTaskTest() throws Exception {
        Task task = new Task("Phil's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"));
        when(taskEndpointService.saveTask(any())).thenReturn(task);
        mockMvc.perform(post("/api/tasks").with(httpBasic("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.timestamp", startsWith(task.getTimestamp().toString().substring(0, 10))));
    }

    @Test
    public void saveTaskThrowsError() throws Exception {
        Task task = new Task("Phil's Task", "Test description", Timestamp.valueOf("2023-04-20 02:00:00.0"));
        when(taskEndpointService.saveTask(any())).thenReturn(null);
        mockMvc.perform(post("/api/tasks").with(httpBasic("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.CONFLICT.value()));
    }

    @Test
    public void updateTaskByNameTest() throws Exception {
        Task task = new Task("El's Task", "Test description", Timestamp.valueOf("2023-04-21 02:00:00.0"));
        when(taskEndpointService.updateTaskByName(any(), any())).thenReturn(task);
        mockMvc.perform(put("/api/tasks/El").with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.timestamp", startsWith(task.getTimestamp().toString().substring(0, 10))));
    }

    @Test
    public void updateTaskByNameThrowsError() throws Exception {
        Task task = new Task("El's Task", "Test description", Timestamp.valueOf("2023-04-21 02:00:00.0"));
        when(taskEndpointService.updateTaskByName(any(), any())).thenReturn(null);
        mockMvc.perform(put("/api/tasks/El").with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void deleteTaskByNameTest() throws Exception {
        Task task = new Task("Emad's Task", "Test description", Timestamp.valueOf("2023-04-22 02:00:00.0"));
        when(taskEndpointService.deleteTaskByName(any())).thenReturn(task);
        mockMvc.perform(delete("/api/tasks/Emad").with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.timestamp", startsWith(task.getTimestamp().toString().substring(0, 10))));
    }

    @Test
    public void deleteTaskByNameThrowsError() throws Exception {
        when(taskEndpointService.deleteTaskByName(any())).thenReturn(null);
        mockMvc.perform(delete("/api/tasks/Emad").with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

}
