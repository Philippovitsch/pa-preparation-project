package com.codecool.tasktracker.repositories;

import com.codecool.tasktracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task getTaskByName(String name);

}
