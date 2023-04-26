package com.codecool.tasktracker.service;

import com.codecool.tasktracker.model.Tag;
import com.codecool.tasktracker.repositories.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TagEndpointServiceTest {

    private TagRepository tagRepository;
    private TagEndpointService tagEndpointService;

    @BeforeEach
    public void init() {
        tagRepository = mock(TagRepository.class);
        tagEndpointService = new TagEndpointService(tagRepository);
    }

    @Test
    public void getAllTagsTest() {
        List<Tag> tags = List.of(
                new Tag("Tag 1", new HashSet<>()),
                new Tag("Tag 2", new HashSet<>()),
                new Tag("Tag 3", new HashSet<>())
        );
        when(tagRepository.findAll()).thenReturn(tags);
        assertEquals(tags, tagEndpointService.getAllTags());
        verify(tagRepository, times(1)).findAll();
    }

}
