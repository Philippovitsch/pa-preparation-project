package com.codecool.tasktracker.service;

import com.codecool.tasktracker.model.Tag;
import com.codecool.tasktracker.repositories.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
                new Tag(1L, "Tag 1"),
                new Tag(2L, "Tag 2"),
                new Tag(3L, "Tag 3")
        );
        when(tagRepository.findAll()).thenReturn(tags);
        assertEquals(tags, tagEndpointService.getAllTags());
        verify(tagRepository, times(1)).findAll();
    }

}
