package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.dto.UserDetailsDto;
import com.codecool.tasktracker.service.UserEndpointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserEndpoint.class)
public class UserEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserEndpointService userEndpointService;

    @Test
    @WithMockUser
    public void getUserDataTest() throws Exception {
        UserDetailsDto userDetailsDto = new UserDetailsDto("user", new ArrayList<>(), true, true, true, true);
        when(userEndpointService.getUserData(any())).thenReturn(userDetailsDto);
        mockMvc.perform(get("/api/user/username"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(userDetailsDto.username())));
    }

    @Test
    @WithMockUser
    public void getUserDataTestThrowsError() throws Exception {
        when(userEndpointService.getUserData(any())).thenReturn(null);
        mockMvc.perform(get("/api/user/username"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

}
