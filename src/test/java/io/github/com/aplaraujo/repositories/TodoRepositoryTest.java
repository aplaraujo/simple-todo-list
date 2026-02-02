package io.github.com.aplaraujo.repositories;

import io.github.com.aplaraujo.entities.Todo;
import io.github.com.aplaraujo.entities.User;
import io.github.com.aplaraujo.entities.enums.PriorityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TodoRepositoryTest {
    @Autowired
    private TodoRepository repository;

    Todo todo, todo1;
    User user;

    @BeforeEach
    void setUp() {
        todo = new Todo("Estudar Java", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", true, PriorityType.ALTA, user);
        todo1 = new Todo("Agendar consulta médica", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", false, PriorityType.ALTA, user);
    }

    @Test
    public void testGivenTodoObject_WhenCreateATodo_ShouldReturnSavedTodoObject() {
        Todo saveTodo = repository.save(todo);
        assertNotNull(saveTodo);
        assertTrue(saveTodo.getId() > 0);
    }

    @Test
    public void testGivenTodoList_WhenFindAllTodos_ShouldReturnTodoList() {
        repository.save(todo);
        repository.save(todo1);
        List<Todo> list = repository.findAll();
        assertNotNull(list);
        assertEquals(8, list.size());
    }

    @Test
    public void testGivenTodoObject_WhenFindATodoById_ShouldReturnTodoObject() {
        repository.save(todo);
        Todo savedTodo = repository.findById(todo.getId()).get();

        assertNotNull(savedTodo);
        assertEquals(savedTodo.getId(), todo.getId());
    }

    @Test
    public void testGivenTodoObject_WhenUpdateTodo_ShouldReturnUpdatedTodo() {
        Todo savedTodo = repository.save(todo);
        savedTodo.setName("Estudar Java e Angular");

        Todo updatedTodo = repository.save(savedTodo);

        assertNotNull(updatedTodo);
        assertEquals("Estudar Java e Angular", updatedTodo.getName());
    }

    @Test
    public void testGivenTodoObject_WhenDeleteATodo_ShouldRemoveTodo() {
        repository.save(todo);
        repository.deleteById(todo.getId());
        Optional<Todo> todoOptional = repository.findById(todo.getId());

        assertTrue(todoOptional.isEmpty());
    }
}