package io.github.com.aplaraujo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"JWT_SECRET_KEY"})
public class TodoControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findAllShouldReturn401ErrorWhenNoUserIsLogged() throws Exception {
        ResultActions result = mockMvc.perform(get("/todos").contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isUnauthorized());
    }
}
