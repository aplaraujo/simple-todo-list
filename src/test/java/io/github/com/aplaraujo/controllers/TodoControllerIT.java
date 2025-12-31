package io.github.com.aplaraujo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.com.aplaraujo.entities.Todo;
import io.github.com.aplaraujo.entities.User;
import io.github.com.aplaraujo.repositories.TodoRepository;
import io.github.com.aplaraujo.repositories.UserRepository;
import io.github.com.aplaraujo.security.UserDetailsImpl;
import io.github.com.aplaraujo.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
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

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private String jwtToken;

    @BeforeEach
    public void setUp() {
        todoRepository.deleteAll();
        userRepository.deleteAll();

        user = new User();
        user.setEmail("mariazinha@gmail.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user = userRepository.save(user);
        jwtToken = generateTokenForUser(user);
    }

    @Test
    public void findAllShouldReturn401ErrorWhenNoUserIsLogged() throws Exception {
        ResultActions result = mockMvc.perform(get("/todos").contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isUnauthorized());
    }

    private String generateTokenForUser(User user) {
        UserDetails userDetails = UserDetailsImpl.build(user);
        return jwtService.generateToken(user.getEmail());
    }
}
