package io.github.com.aplaraujo.controllers;

import io.github.com.aplaraujo.entities.Todo;
import io.github.com.aplaraujo.repositories.TodoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/todos")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public String teste() {
        Optional<Todo> optionalTodo = todoRepository.findById(1L);
        Todo todo = optionalTodo.get();
        return todo.getName();
    }
}
