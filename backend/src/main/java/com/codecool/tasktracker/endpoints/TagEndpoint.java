package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.model.Tag;
import com.codecool.tasktracker.service.TagEndpointService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagEndpoint {

    private final TagEndpointService tagEndpointService;

    public TagEndpoint(TagEndpointService tagEndpointService) {
        this.tagEndpointService = tagEndpointService;
    }

    @GetMapping("/all")
    public List<Tag> getAllTags() {
        return tagEndpointService.getAllTags();
    }

}
