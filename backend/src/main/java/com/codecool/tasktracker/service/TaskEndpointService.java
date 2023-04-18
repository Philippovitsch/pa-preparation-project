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

    public Task getTaskByName(String name) {
        return taskRepository.getTaskByName(name);
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTaskByName(String name, Task updatedTask) {
        Task task = taskRepository.getTaskByName(name);
        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setTimestamp(updatedTask.getTimestamp());
        return taskRepository.save(task);
    }

    public void deleteTaskByName(String name) {
        Task task = getTaskByName(name);
        taskRepository.delete(task);
    }

}
