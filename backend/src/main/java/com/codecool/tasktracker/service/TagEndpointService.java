package com.codecool.tasktracker.service;

import com.codecool.tasktracker.model.Tag;
import com.codecool.tasktracker.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagEndpointService {

    private final TagRepository tagRepository;

    public TagEndpointService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

}
