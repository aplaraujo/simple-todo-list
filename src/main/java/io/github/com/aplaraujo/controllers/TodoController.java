package io.github.com.aplaraujo.controllers;

import io.github.com.aplaraujo.dto.TodoDTO;
import io.github.com.aplaraujo.entities.Todo;
import io.github.com.aplaraujo.mappers.TodoMapper;
import io.github.com.aplaraujo.security.UserDetailsImpl;
import io.github.com.aplaraujo.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/todos")
@RequiredArgsConstructor
public class TodoController implements GenericController{

    private final TodoService service;
    private final TodoMapper mapper;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody TodoDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        service.save(dto);
        var url = generateHeaderLocation(dto.id());
        return ResponseEntity.created(url).build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> findById(@PathVariable("id") String id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        var todoId = Long.parseLong(id);
        return service.getTodoByIdAndUser(todoId, userId)
                .map(todo -> ResponseEntity.ok(mapper.toDTO(todo)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        List<Todo> todos = service.getAllTodosByUser(userId);
        return ResponseEntity.ok(todos);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable("id") String id,
            @RequestBody TodoDTO dto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        var todoId = Long.parseLong(id);
        Long userId = userDetails.getId();
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
    public ResponseEntity<Object> delete(@PathVariable("id") String id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        var todoId = Long.parseLong(id);
        Long userId = userDetails.getId();
        return service.getTodoByIdAndUser(todoId, userId).map(todo -> {
            service.delete(todoId);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
