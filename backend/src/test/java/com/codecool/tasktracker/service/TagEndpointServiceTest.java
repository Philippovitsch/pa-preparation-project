package com.codecool.tasktracker.service;

import com.codecool.tasktracker.model.Tag;
import com.codecool.tasktracker.repositories.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagEndpointServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagEndpointService tagEndpointService;

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
