package com.codecool.tasktracker;

import com.codecool.tasktracker.service.InitService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskTrackerApplication {

	private final InitService initService;

	public TaskTrackerApplication(InitService initService) {
		this.initService = initService;
	}

	@PostConstruct
	public void initialize() {
		initService.createSampleTasks();
	}

	public static void main(String[] args) {
		SpringApplication.run(TaskTrackerApplication.class, args);
	}

}
