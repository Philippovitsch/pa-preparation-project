package com.codecool.tasktracker.dto;

import com.codecool.tasktracker.model.Tag;

import java.sql.Timestamp;
import java.util.Set;

public record TaskDto(
        String user,
        String name,
        String description,
        Timestamp timestamp,
        Set<Tag> tags
) {}
