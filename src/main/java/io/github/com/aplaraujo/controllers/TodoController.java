package io.github.com.aplaraujo.controllers;

import io.github.com.aplaraujo.dto.TodoDTO;
import io.github.com.aplaraujo.mappers.TodoMapper;
import io.github.com.aplaraujo.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/todos")
@RequiredArgsConstructor
public class TodoController implements GenericController{

    private final TodoService service;
    private final TodoMapper mapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody TodoDTO dto) {
        service.save(dto);
        var url = generateHeaderLocation(dto.id());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> findById(@PathVariable("id") String id) {
        var todoId = Long.parseLong(id);
        return service.findById(todoId).map(todo -> {
            var dto = mapper.toDTO(todo);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<TodoDTO> todos() {
        return service.todos();
    }
}
