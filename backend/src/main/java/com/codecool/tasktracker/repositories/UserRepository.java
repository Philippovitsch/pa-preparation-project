package com.codecool.tasktracker.repositories;

import com.codecool.tasktracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    boolean existsByUsername(String username);

}
