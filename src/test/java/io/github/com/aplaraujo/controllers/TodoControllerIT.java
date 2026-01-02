package io.github.com.aplaraujo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.com.aplaraujo.dto.TodoDTO;
import io.github.com.aplaraujo.entities.enums.PriorityType;
import io.github.com.aplaraujo.tests.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "JWT_SECRET_KEY=O94GzKJ0OdH17nGyue5DMHsgZIeRfRdsOSEbKljTPzX",
        "JWT_DURATION=86400"
})
@AutoConfigureMockMvc
@Transactional
public class TodoControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String userToken, email, password;
    private Long existentTodoId;
    private Long existentUserId;

    @BeforeEach
    void setUp() throws Exception {
        email = "ritinha@gmail.com";
        password = "123456";
        userToken = tokenUtil.obtainAccessTokenForTest(mockMvc, email, password);
        existentTodoId = 1L;
    }

    @Test
    public void shouldReturnAListOfTodosWhenAUserIsLogged() throws Exception {
        ResultActions result = mockMvc.perform(get("/todos").header("Authorization", "Bearer " + userToken).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    public void shouldReturnATodoByIdWhenAUserIsLogged() throws Exception {
        ResultActions result = mockMvc.perform(get("/todos/" + existentTodoId).header("Authorization", "Bearer " + userToken).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existentTodoId));
        result.andExpect(jsonPath("$.name").value("Lavar a louça"));
        result.andExpect(jsonPath("$.description").value("Lorem ipsum dolor sit amet"));
        result.andExpect(jsonPath("$.done").value(true));
        result.andExpect(jsonPath("$.priority").value("BAIXA"));
    }

    @Test
    public void shouldCreateATodoWhenAUserIsLogged() throws Exception {
        TodoDTO dto = new TodoDTO(8L, "Comprar leite", "Lorem ipsum dolor sit amet", true, PriorityType.MEDIA, 1L);
        ResultActions result = mockMvc.perform(post("/todos").header("Authorization", "Bearer " + userToken)
                .content(objectMapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated());
    }

    @Test
    public void shouldUpdateATodoWhenAUserIsLogged() throws Exception {
        Long todoId = 8L;
        TodoDTO dto = new TodoDTO(todoId, "Comprar leite", "Atualizado", true, PriorityType.BAIXA, 1L);
        ResultActions result = mockMvc.perform(put("/todos/" + existentTodoId).header("Authorization", "Bearer " + userToken)
                .content(objectMapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteATodoWhenAUserIsLogged() throws Exception {
        ResultActions result = mockMvc.perform(delete("/todos/" + existentTodoId).header("Authorization", "Bearer " + userToken).contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn401ErrorWhenNoUserIsLogged() throws Exception {
        ResultActions result = mockMvc.perform(get("/todos").accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isUnauthorized());
    }

}
