package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.service.TaskEndpointService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return taskEndpointService.getTaskByName(name);
    }

    @PostMapping
    public Task saveTask(@RequestBody Task task) {
        return taskEndpointService.saveTask(task);
    }

    @PutMapping("/{name}")
    public Task updateTaskByName(@PathVariable String name, @RequestBody Task updatedTask) {
        return taskEndpointService.updateTaskByName(name, updatedTask);
    }

    @DeleteMapping("/{name}")
    public void deleteTaskByName(@PathVariable String name) {
        taskEndpointService.deleteTaskByName(name);
    }

}
