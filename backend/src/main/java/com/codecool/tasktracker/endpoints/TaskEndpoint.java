package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.exceptions.TaskAlreadyExistsException;
import com.codecool.tasktracker.exceptions.TaskNotFoundException;
import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.service.TaskEndpointService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
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

    @GetMapping("/{name}")
    public Task getTaskByName(@PathVariable String name) {
        Task task = taskEndpointService.getTaskByName(name);
        if (task == null) throw new TaskNotFoundException("Task " + name + " not found!");
        return task;
    }

    @PostMapping
    public Task saveTask(@RequestBody Task newTask) {
        Task task = taskEndpointService.saveTask(newTask);
        if (task == null) throw new TaskAlreadyExistsException("Task already exists!");
        return task;
    }

    @PutMapping("/{name}")
    public Task updateTaskByName(@PathVariable String name, @RequestBody Task updatedTask) {
        Task task = taskEndpointService.updateTaskByName(name, updatedTask);
        if (task == null) throw new TaskNotFoundException("Task " + name + " not found!");
        return task;
    }

    @DeleteMapping("/{name}")
    public Task deleteTaskByName(@PathVariable String name) {
        Task task = taskEndpointService.deleteTaskByName(name);
        if (task == null) throw new TaskNotFoundException("Task " + name + " not found!");
        return task;
    }

}
