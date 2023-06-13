package com.codecool.tasktracker.service;

import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.model.User;
import com.codecool.tasktracker.repositories.TaskRepository;
import com.codecool.tasktracker.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskEndpointService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskEndpointService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return (user != null) ? taskRepository.getTaskByUserId(user.getId()) : new ArrayList<>();
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
