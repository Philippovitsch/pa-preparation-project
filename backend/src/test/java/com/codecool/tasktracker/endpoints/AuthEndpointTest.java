package com.codecool.tasktracker.endpoints;

import com.codecool.tasktracker.dto.AuthenticationRequestDto;
import com.codecool.tasktracker.security.JwtUtils;
import com.codecool.tasktracker.security.SecurityConfig;
import com.codecool.tasktracker.security.UserConfig;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthEndpoint.class)
@Import({SecurityConfig.class, UserConfig.class, JwtUtils.class})
public class AuthEndpointTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AuthEndpointService authEndpointService;

    @Test
    public void userAuthenticationSuccessTest() throws Exception {
        AuthenticationRequestDto loginData = new AuthenticationRequestDto("user", "user");
        when(authEndpointService.authenticate(any())).thenReturn("testToken");
        MvcResult result = mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("testToken", result.getResponse().getContentAsString());
    }

    @Test
    public void userAuthenticationWrongPasswordThrowsError() throws Exception {
        AuthenticationRequestDto loginData = new AuthenticationRequestDto("user", "wrong-password");
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
        AuthenticationRequestDto loginData = new AuthenticationRequestDto("wrong-user", "user");
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
        AuthenticationRequestDto signUpData = new AuthenticationRequestDto("user-2", "user-2");
        when(authEndpointService.signUp(any())).thenReturn(true);
        mockMvc.perform(post("/api/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpData))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(signUpData.getUsername())))
                .andExpect(jsonPath("$.password", is(signUpData.getPassword())));
    }

    @Test
    public void signUpExistingUsernameThrowsError() throws Exception {
        AuthenticationRequestDto signUpData = new AuthenticationRequestDto("user", "user");
        when(authEndpointService.signUp(any())).thenReturn(false);
        mockMvc.perform(post("/api/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpData))
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.CONFLICT.value()));
    }

}
