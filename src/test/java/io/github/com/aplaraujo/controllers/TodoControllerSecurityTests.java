package io.github.com.aplaraujo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.com.aplaraujo.dto.TodoDTO;
import io.github.com.aplaraujo.entities.enums.PriorityType;
import io.github.com.aplaraujo.mappers.TodoMapper;
import io.github.com.aplaraujo.security.JwtAuthFilter;
import io.github.com.aplaraujo.security.UserDetailsImpl;
import io.github.com.aplaraujo.services.JwtService;
import io.github.com.aplaraujo.services.TodoService;
import io.github.com.aplaraujo.tests.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc
public class TodoControllerSecurityTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @MockitoBean
    private TodoMapper todoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TokenUtil tokenUtil;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;

    @MockitoBean
    private UserDetailsService userDetailsService;

    private Long existentTodoId;

    @BeforeEach
    void setUp() {
        existentTodoId = 1L;
    }

    @Test
    @WithMockUser(username = "mariazinha@gmail.com")
    public void getTodoByUserIdAndIdWithAuthReturns200() throws Exception {
        mockMvc.perform(get("/todos/{id}", existentTodoId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "mariazinha@gmail.com")
    public void getTodosByUserIdWithAuthReturns200() throws Exception {
        mockMvc.perform(get("/todos").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void getTodoByUserIdAndIdWithoutAuthReturns401() throws Exception {
        mockMvc.perform(get("/todos/{id}", existentTodoId)).andExpect(status().isUnauthorized());
    }
}
