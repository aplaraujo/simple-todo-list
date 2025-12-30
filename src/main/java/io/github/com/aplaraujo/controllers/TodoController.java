package io.github.com.aplaraujo.controllers;

import io.github.com.aplaraujo.dto.TodoDTO;
import io.github.com.aplaraujo.entities.Todo;
import io.github.com.aplaraujo.mappers.TodoMapper;
import io.github.com.aplaraujo.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/{userId}/todos")
@RequiredArgsConstructor
public class TodoController implements GenericController{

    private final TodoService service;
    private final TodoMapper mapper;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody TodoDTO dto) {
        service.save(dto);
        var url = generateHeaderLocation(dto.id());
        return ResponseEntity.created(url).build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Todo>> findById(@PathVariable("id") String id, @PathVariable("userId") String userId) {
        var todoId = Long.parseLong(id);
        var todoUserUd = Long.parseLong(userId);
        Optional<Todo> todo = service.getTodoByIdAndUser(todoId, todoUserUd);
        return ResponseEntity.ok(todo);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(@PathVariable Long userId) {
        List<Todo> todos = service.getAllTodosByUser(userId);
        return ResponseEntity.ok(todos);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable("userId") String userId,
            @PathVariable("id") String id,
            @RequestBody TodoDTO dto) {

        var todoId = Long.parseLong(id);
        var todoUserUd = Long.parseLong(userId);
        return service.getTodoByIdAndUser(todoId, todoUserUd).map(todo -> {
            Todo entity = mapper.toEntity(dto);
            todo.setName(entity.getName());
            todo.setDescription(entity.getDescription());
            todo.setDone(entity.getDone());
            todo.setPriority(entity.getPriority());
            service.update(todo);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id, @PathVariable("userId") String userId) {
        var todoId = Long.parseLong(id);
        var todoUserUd = Long.parseLong(userId);
        return service.getTodoByIdAndUser(todoId, todoUserUd).map(todo -> {
            service.delete(todo);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
