package com.codecool.tasktracker.service;

import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskEndpointService {

    private final TaskRepository taskRepository;

    public TaskEndpointService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByUsername(String username) {
        return taskRepository.getTaskByUsername(username);
    }

    public Task getTaskByName(String name) {
        return taskRepository.getTaskByName(name);
    }

    public Task saveTask(Task newTask) {
        Task task = taskRepository.getTaskByName(newTask.getName());
        return (task == null) ? taskRepository.save(newTask) : null;
    }

    public Task updateTaskByName(String name, Task updatedTask) {
        Task task = taskRepository.getTaskByName(name);
        if (task != null) {
            task.setName(updatedTask.getName());
            task.setDescription(updatedTask.getDescription());
            task.setTimestamp(updatedTask.getTimestamp());
            taskRepository.save(task);
        }
        return task;
    }

    public Task deleteTaskByName(String name) {
        Task task = taskRepository.getTaskByName(name);
        if (task != null) taskRepository.delete(task);
        return task;
    }

}
