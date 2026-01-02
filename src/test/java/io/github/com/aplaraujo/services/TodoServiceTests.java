package io.github.com.aplaraujo.services;

import io.github.com.aplaraujo.repositories.TodoRepository;
import io.github.com.aplaraujo.services.exceptions.ResourceNotFoundException;
import io.github.com.aplaraujo.tests.TokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestPropertySource(properties = {"JWT_SECRET_KEY=O94GzKJ0OdH17nGyue5DMHsgZIeRfRdsOSEbKljTPzX"})
@Transactional
public class TodoServiceTests {
    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @MockitoBean
    private TokenUtil tokenUtil;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalTodos;
    private Long userId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        userId = 1L;
        nonExistingId = 1000L;
        countTotalTodos = 6L;
    }

    @Test
    public void deleteShouldDeleteTodoWhenIdExists() {

        todoService.delete(existingId);

        Assertions.assertEquals(countTotalTodos - 1, todoRepository.count());
    }

    @Test
    public void deleteShouldThrowTodoNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            todoService.delete(nonExistingId);
        });
    }

    @Test
    public void findAllShouldReturnAllTodos() {
        todoService.getAllTodosByUser(userId);
        Assertions.assertEquals(countTotalTodos, todoRepository.count());
    }

}
