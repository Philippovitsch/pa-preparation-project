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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskEndpointService {

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final AuthContextUtil authContextUtil;

    public TaskEndpointService(TaskRepository taskRepository, TagRepository tagRepository, UserRepository userRepository, AuthContextUtil authContextUtil) {
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
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

    public Task saveTask(TaskDto taskDto) throws IOException {
        Task task = taskRepository.getTaskByName(taskDto.name());
        User user = userRepository.findUserByUsername(taskDto.user());

        if (task != null || user == null) {
            return null;
        }

        Set<Tag> tags = taskDto.tags().stream().map(tagRepository::getTagByName).collect(Collectors.toSet());
        String imageName = (taskDto.image().isPresent()) ? taskDto.image().get().getOriginalFilename() : null;
        String imageType = (taskDto.image().isPresent()) ? taskDto.image().get().getContentType() : null;
        byte[] imageData = (taskDto.image().isPresent()) ? taskDto.image().get().getBytes() : null;

        Task newTask = new Task();
        newTask.setUser(user);
        newTask.setName(taskDto.name());
        newTask.setDescription(taskDto.description());
        newTask.setTimestamp(taskDto.timestamp());
        newTask.setTags(tags);
        newTask.setImageName(imageName);
        newTask.setImageType(imageType);
        newTask.setImageData(imageData);

        return taskRepository.save(newTask);
    }

    public Task updateTaskByName(String name, TaskDto updatedTask) throws IOException {
        Task task = taskRepository.getTaskByName(name);

        if (task == null) {
            return null;
        }

        if (authContextUtil.isTaskOwnerOrAdmin(task)) {
            Set<Tag> tags = updatedTask.tags().stream().map(tagRepository::getTagByName).collect(Collectors.toSet());
            String imageName = (updatedTask.image().isPresent()) ? updatedTask.image().get().getOriginalFilename() : null;
            String imageType = (updatedTask.image().isPresent()) ? updatedTask.image().get().getContentType() : null;
            byte[] imageData = (updatedTask.image().isPresent()) ? updatedTask.image().get().getBytes() : null;

            task.setName(updatedTask.name());
            task.setDescription(updatedTask.description());
            task.setTags(tags);
            task.setImageName(imageName);
            task.setImageType(imageType);
            task.setImageData(imageData);

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
