package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.model.Tag;
import com.codecool.tasktracker.security.SecurityConfig;
import com.codecool.tasktracker.service.TagEndpointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagEndpoint.class)
@Import(SecurityConfig.class)
public class TagEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtEncoder jwtEncoder;

    @MockBean
    private TagEndpointService tagEndpointService;

    private String generateBearerToken() {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject("admin")
                .claim("roles", "ROLE_USER ROLE_ADMIN")
                .build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return "Bearer " + token;
    }

    @Test
    public void getAllTagsTest() throws Exception {
        List<Tag> tags = List.of(
                new Tag("Tag 1", new HashSet<>()),
                new Tag("Tag 2", new HashSet<>()),
                new Tag("Tag 3", new HashSet<>())
        );
        when(tagEndpointService.getAllTags()).thenReturn(tags);
        mockMvc.perform(get("/api/tags/all")
                        .header("Authorization", generateBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[0].name", is(tags.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(tags.get(1).getName())))
                .andExpect(jsonPath("$[2].name", is(tags.get(2).getName())));
    }

}
