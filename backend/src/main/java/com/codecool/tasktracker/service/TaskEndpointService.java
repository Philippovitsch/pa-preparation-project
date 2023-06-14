package com.codecool.tasktracker.service;

import com.codecool.tasktracker.dto.TaskDto;
import com.codecool.tasktracker.exceptions.AuthenticationException;
import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.model.User;
import com.codecool.tasktracker.repositories.TaskRepository;
import com.codecool.tasktracker.repositories.UserRepository;
import com.codecool.tasktracker.security.AuthContextUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskEndpointService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final AuthContextUtil authContextUtil;

    public TaskEndpointService(TaskRepository taskRepository, UserRepository userRepository, AuthContextUtil authContextUtil) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.authContextUtil = authContextUtil;
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

    public Task saveTask(TaskDto taskDto) {
        Task task = taskRepository.getTaskByName(taskDto.name());
        User user = userRepository.findUserByUsername(taskDto.user());

        if (task != null || user == null) {
            return null;
        }

        Task newTask = new Task();
        newTask.setUser(user);
        newTask.setName(taskDto.name());
        newTask.setDescription(taskDto.description());
        newTask.setTimestamp(taskDto.timestamp());
        newTask.setTags(taskDto.tags());

        return taskRepository.save(newTask);
    }

    public Task updateTaskByName(String name, TaskDto updatedTask) {
        Task task = taskRepository.getTaskByName(name);

        if (task == null) {
            return null;
        }

        if (authContextUtil.isTaskOwnerOrAdmin(task)) {
            task.setName(updatedTask.name());
            task.setDescription(updatedTask.description());
            task.setTags(updatedTask.tags());
            taskRepository.save(task);
            return task;
        }

        throw new AuthenticationException("You are not allowed to edit this task");
    }

    public Task deleteTaskByName(String name) {
        Task task = taskRepository.getTaskByName(name);

        if (task == null) {
            return null;
        }

        if (authContextUtil.isTaskOwnerOrAdmin(task)) {
            taskRepository.delete(task);
            return task;
        }

        throw new AuthenticationException("You are not allowed to delete this task");
    }

}
