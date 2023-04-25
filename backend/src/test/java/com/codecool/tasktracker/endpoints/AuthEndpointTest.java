package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.dto.TokenDto;
import com.codecool.tasktracker.dto.UserDataDto;
import com.codecool.tasktracker.security.SecurityConfig;
import com.codecool.tasktracker.service.AuthEndpointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthEndpoint.class)
@Import(SecurityConfig.class)
public class AuthEndpointTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AuthEndpointService authEndpointService;

    @Test
    public void userAuthenticationSuccessTest() throws Exception {
        UserDataDto loginData = new UserDataDto("user", "user");
        TokenDto tokenData = new TokenDto("user", "testToken");
        when(authEndpointService.authenticate(any())).thenReturn(tokenData);
        MvcResult result = mockMvc.perform(post("/api/auth/authenticate").with(httpBasic("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(objectMapper.writeValueAsString(tokenData), result.getResponse().getContentAsString());
    }

    @Test
    public void userAuthenticationWrongPasswordThrowsError() throws Exception {
        UserDataDto loginData = new UserDataDto("user", "wrong-password");
        when(authEndpointService.authenticate(any())).thenReturn(null);
        mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData))
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void userAuthenticationWrongUsernameThrowsError() throws Exception {
        UserDataDto loginData = new UserDataDto("wrong-user", "user");
        when(authEndpointService.authenticate(any())).thenReturn(null);
        mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData))
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void signUpSuccessTest() throws Exception {
        UserDataDto signUpData = new UserDataDto("user-2", "user-2");
        when(authEndpointService.signUp(any())).thenReturn(signUpData);
        mockMvc.perform(post("/api/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpData))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(signUpData.username())))
                .andExpect(jsonPath("$.password", is(signUpData.password())));
    }

    @Test
    public void signUpExistingUsernameThrowsError() throws Exception {
        UserDataDto signUpData = new UserDataDto("user", "user");
        when(authEndpointService.signUp(any())).thenReturn(null);
        mockMvc.perform(post("/api/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpData))
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.CONFLICT.value()));
    }

}
