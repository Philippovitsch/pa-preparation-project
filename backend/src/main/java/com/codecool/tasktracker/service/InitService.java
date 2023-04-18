package com.codecool.tasktracker.service;

import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class InitService {

    private final TaskRepository taskRepository;

    public InitService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createSampleTasks() {
        Task shopping = new Task("Shopping", "Buy some new Jeans", Timestamp.valueOf("2023-04-16 00:00:00.0"));
        Task cleanUp = new Task("Clean up", "Clean up apartment", Timestamp.valueOf("2023-04-17 00:00:00.0"));
        Task coding = new Task("Coding", "Code a pet project", Timestamp.valueOf("2023-04-18 00:00:00.0"));

        taskRepository.saveAll(List.of(shopping, cleanUp, coding));
    }

}
