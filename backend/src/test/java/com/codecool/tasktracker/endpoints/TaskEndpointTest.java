package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.dto.TaskDto;
import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.service.TaskEndpointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
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

    @MockBean
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
    @WithMockUser
    public void getAllTasksTest() throws Exception {
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
        when(taskEndpointService.getTaskByName(any())).thenReturn(task);
        mockMvc.perform(get("/api/tasks/John"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())));
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
        when(taskEndpointService.saveTask(any())).thenReturn(task);
        mockMvc.perform(multipart("/api/tasks")
                        .param("user", taskDto.user())
                        .param("name", taskDto.name())
                        .param("description", taskDto.description())
                        .param("timestamp", String.valueOf(taskDto.timestamp().getTime()))
                        .param("tags", taskDto.tags().toString())
                        .param("isDone", String.valueOf(taskDto.isDone()))
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())));
    }

    @Test
    @WithMockUser
    public void saveTaskThrowsError() throws Exception {
        when(taskEndpointService.saveTask(any())).thenReturn(null);
        mockMvc.perform(post("/api/tasks")
                        .param("user", taskDto.user())
                        .param("name", taskDto.name())
                        .param("description", taskDto.description())
                        .param("timestamp", String.valueOf(taskDto.timestamp().getTime()))
                        .param("tags", taskDto.tags().toString())
                        .param("isDone", String.valueOf(taskDto.isDone()))
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.CONFLICT.value()));
    }

    @Test
    @WithMockUser
    public void updateTaskByNameTest() throws Exception {
        when(taskEndpointService.updateTaskByName(any(), any())).thenReturn(task);
        mockMvc.perform(multipart("/api/tasks/TestTask")
                        .param("user", taskDto.user())
                        .param("name", taskDto.name())
                        .param("description", taskDto.description())
                        .param("timestamp", String.valueOf(taskDto.timestamp().getTime()))
                        .param("tags", taskDto.tags().toString())
                        .param("isDone", String.valueOf(taskDto.isDone()))
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())));
    }

    @Test
    @WithMockUser
    public void updateTaskByNameThrowsError() throws Exception {
        when(taskEndpointService.updateTaskByName(any(), any())).thenReturn(null);
        mockMvc.perform(multipart("/api/tasks/TestTask")
                        .param("user", taskDto.user())
                        .param("name", taskDto.name())
                        .param("description", taskDto.description())
                        .param("timestamp", String.valueOf(taskDto.timestamp().getTime()))
                        .param("tags", taskDto.tags().toString())
                        .param("isDone", String.valueOf(taskDto.isDone()))
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @WithMockUser
    public void toggleIsDoneTest() throws Exception {
        when(taskEndpointService.toggleIsDone(any(), anyBoolean())).thenReturn(task);
        mockMvc.perform(put("/api/tasks/TestTask/false").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.isDone", is(task.isDone())));
    }

    @Test
    @WithMockUser
    public void toggleIsDoneThrowsError() throws Exception {
        when(taskEndpointService.toggleIsDone(any(), anyBoolean())).thenReturn(null);
        mockMvc.perform(put("/api/tasks/TestTask/false").with(csrf()))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @WithMockUser
    public void deleteTaskByNameTest() throws Exception {
        when(taskEndpointService.deleteTaskByName(any())).thenReturn(task);
        mockMvc.perform(delete("/api/tasks/Emad").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())));
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
