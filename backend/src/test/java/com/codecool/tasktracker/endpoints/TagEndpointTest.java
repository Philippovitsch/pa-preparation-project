package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.model.Tag;
import com.codecool.tasktracker.service.TagEndpointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagEndpoint.class)
public class TagEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagEndpointService tagEndpointService;

    @Test
    @WithMockUser
    public void getAllTagsTest() throws Exception {
        List<Tag> tags = List.of(
                new Tag("Tag 1", new HashSet<>()),
                new Tag("Tag 2", new HashSet<>()),
                new Tag("Tag 3", new HashSet<>())
        );
        when(tagEndpointService.getAllTags()).thenReturn(tags);
        mockMvc.perform(get("/api/tags/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[0].name", is(tags.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(tags.get(1).getName())))
                .andExpect(jsonPath("$[2].name", is(tags.get(2).getName())));
    }

}
