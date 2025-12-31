package io.github.com.aplaraujo.controllers;

import io.github.com.aplaraujo.dto.TodoDTO;
import io.github.com.aplaraujo.entities.Todo;
import io.github.com.aplaraujo.mappers.TodoMapper;
import io.github.com.aplaraujo.security.UserDetailsImpl;
import io.github.com.aplaraujo.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/todos")
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
    public ResponseEntity<Optional<Todo>> findById(@PathVariable("id") String id, Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        var todoId = Long.parseLong(id);
        Optional<Todo> todo = service.getTodoByIdAndUser(todoId, userId);
        return ResponseEntity.ok(todo);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        List<Todo> todos = service.getAllTodosByUser(userId);
        return ResponseEntity.ok(todos);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable("id") String id,
            @RequestBody TodoDTO dto,
            Authentication authentication) {

        var todoId = Long.parseLong(id);
        Long userId = getUserIdFromAuthentication(authentication);
        return service.getTodoByIdAndUser(todoId, userId).map(todo -> {
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
    public ResponseEntity<Object> delete(@PathVariable("id") String id, Authentication authentication) {
        var todoId = Long.parseLong(id);
        Long userId = getUserIdFromAuthentication(authentication);
        return service.getTodoByIdAndUser(todoId, userId).map(todo -> {
            service.delete(todo);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getId();
    }
}
