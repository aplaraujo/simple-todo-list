package io.github.com.aplaraujo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.com.aplaraujo.dto.TodoDTO;
import io.github.com.aplaraujo.entities.Todo;
import io.github.com.aplaraujo.entities.User;
import io.github.com.aplaraujo.entities.enums.PriorityType;
import io.github.com.aplaraujo.mappers.TodoMapper;
import io.github.com.aplaraujo.security.JwtAuthFilter;
import io.github.com.aplaraujo.security.UserDetailsImpl;
import io.github.com.aplaraujo.services.JwtService;
import io.github.com.aplaraujo.services.TodoService;
import io.github.com.aplaraujo.tests.Factory;
import io.github.com.aplaraujo.tests.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.Transient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TodoControllerTests {
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
        existentTodoId = 2L;
    }


    @Test
    @WithMockUser(username = "mariazinha@gmail.com", roles = "USER")
    public void saveShouldReturn201WhenUserIsLogged() throws Exception {
        TodoDTO dto = new TodoDTO(2L, "Ir ao supermercado", "Sed ut perspiciatis unde omnis iste natus error", true, PriorityType.BAIXA, 3L);
        mockMvc.perform(post("/todos").content(objectMapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


}
