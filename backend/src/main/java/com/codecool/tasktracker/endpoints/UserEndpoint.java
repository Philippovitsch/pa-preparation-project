package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.dto.UserDetailsDto;
import com.codecool.tasktracker.exceptions.UserNotFoundException;
import com.codecool.tasktracker.service.UserEndpointService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserEndpoint {

    private final UserEndpointService userEndpointService;

    public UserEndpoint(UserEndpointService userEndpointService) {
        this.userEndpointService = userEndpointService;
    }

    @GetMapping("/{username}")
    public UserDetailsDto getUserData(@PathVariable String username) {
        UserDetailsDto userData = userEndpointService.getUserData(username);
        if (userData == null) throw new UserNotFoundException("User not found!");
        return userData;
    }

}
