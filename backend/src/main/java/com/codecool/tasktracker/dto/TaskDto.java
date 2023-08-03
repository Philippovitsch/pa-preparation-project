package com.codecool.tasktracker.dto;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;

public record TaskDto(
        String user,
        String name,
        String description,
        Timestamp timestamp,
        Set<String> tags,
        Optional<MultipartFile> image,
        boolean isDone
) {}
