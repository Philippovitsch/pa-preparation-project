package com.codecool.tasktracker.security;

import com.codecool.tasktracker.model.Task;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthContextUtil {

    public boolean isTaskOwnerOrAdmin(Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isTaskOwner = authentication.getName().equals(task.getUser().getUsername());
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        return isTaskOwner || isAdmin;
    }

}
