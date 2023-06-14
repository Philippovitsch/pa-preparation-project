package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.dto.TokenDto;
import com.codecool.tasktracker.dto.UserDataDto;
import com.codecool.tasktracker.model.User;
import com.codecool.tasktracker.service.AuthEndpointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthEndpoint.class)
public class AuthEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthEndpointService authEndpointService;

    @Test
    @WithMockUser
    public void userAuthenticationSuccessTest() throws Exception {
        TokenDto tokenData = new TokenDto("user", "testToken");
        when(authEndpointService.authenticate(any())).thenReturn(tokenData);
        MvcResult result = mockMvc.perform(post("/api/auth/authenticate").with(httpBasic("user", "password")).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(objectMapper.writeValueAsString(tokenData), result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void userAuthenticationWithoutCredentialsThrowsError() throws Exception {
        when(authEndpointService.authenticate(any())).thenReturn(null);
        mockMvc.perform(post("/api/auth/authenticate").with(csrf()))
                .andDo(print())
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }


    @Test
    @WithMockUser
    public void userAuthenticationWrongPasswordThrowsError() throws Exception {
        when(authEndpointService.authenticate(any())).thenReturn(null);
        mockMvc.perform(post("/api/auth/authenticate").with(httpBasic("user", "wrong-password")).with(csrf()))
                .andDo(print())
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser
    public void userAuthenticationWrongUsernameThrowsError() throws Exception {
        when(authEndpointService.authenticate(any())).thenReturn(null);
        mockMvc.perform(post("/api/auth/authenticate").with(httpBasic("wrong-user", "password")).with(csrf()))
                .andDo(print())
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    @WithMockUser
    public void signUpSuccessTest() throws Exception {
        UserDataDto signUpData = new UserDataDto("user", "password");
        User user = new User();
        user.setId(0);
        user.setUsername("user");

        when(authEndpointService.signUp(any())).thenReturn(user);
        mockMvc.perform(post("/api/auth/sign-up").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpData))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) user.getId())))
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }

    @Test
    @WithMockUser
    public void signUpExistingUsernameThrowsError() throws Exception {
        UserDataDto signUpData = new UserDataDto("user", "password");
        when(authEndpointService.signUp(any())).thenReturn(null);
        mockMvc.perform(post("/api/auth/sign-up").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpData))
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.CONFLICT.value()));
    }

}
