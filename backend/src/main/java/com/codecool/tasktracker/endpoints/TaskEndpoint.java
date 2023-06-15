package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.dto.TaskDto;
import com.codecool.tasktracker.exceptions.TaskAlreadyExistsException;
import com.codecool.tasktracker.exceptions.TaskNotFoundException;
import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.service.TaskEndpointService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/tasks")
public class TaskEndpoint {

    private final TaskEndpointService taskEndpointService;

    public TaskEndpoint(TaskEndpointService taskEndpointService) {
        this.taskEndpointService = taskEndpointService;
    }

    @GetMapping("/all")
    public List<Task> getAllTasks() {
        return taskEndpointService.getAllTasks();
    }

    @GetMapping("/user/{username}")
    public List<Task> getTaskByUsername(@PathVariable String username) {
        return taskEndpointService.getTasksByUsername(username);
    }

    @GetMapping("/{name}")
    public Task getTaskByName(@PathVariable String name) {
        Task task = taskEndpointService.getTaskByName(name);
        if (task == null) throw new TaskNotFoundException("Task " + name + " not found!");
        return task;
    }

    @PostMapping
    public Task saveTask(
            @RequestParam("user") String user,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("timestamp") long timestamp,
            @RequestParam("tags") Set<String> tags,
            @RequestParam("image") Optional<MultipartFile> image
    ) throws IOException {
        TaskDto newTask = new TaskDto(user, name, description, new Timestamp(timestamp), tags, image);
        Task task = taskEndpointService.saveTask(newTask);
        if (task == null) throw new TaskAlreadyExistsException("Task already exists!");
        return task;
    }

    @PutMapping("/{taskName}")
    public Task updateTaskByName(
            @PathVariable String taskName,
            @RequestParam("user") String user,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("timestamp") long timestamp,
            @RequestParam("tags") Set<String> tags,
            @RequestParam("image") Optional<MultipartFile> image

    ) throws IOException {
        TaskDto updatedTask = new TaskDto(user, name, description, new Timestamp(timestamp), tags, image);
        Task task = taskEndpointService.updateTaskByName(taskName, updatedTask);
        if (task == null) throw new TaskNotFoundException("Task " + taskName + " not found!");
        return task;
    }

    @DeleteMapping("/{name}")
    public Task deleteTaskByName(@PathVariable String name) {
        Task task = taskEndpointService.deleteTaskByName(name);
        if (task == null) throw new TaskNotFoundException("Task " + name + " not found!");
        return task;
    }

}
