package com.codecool.tasktracker.dto;

import java.util.Set;

public record TokenDto(String username, Set<String> roles, String token) {
}
