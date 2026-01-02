package io.github.com.aplaraujo.services;

import io.github.com.aplaraujo.dto.TodoDTO;
import io.github.com.aplaraujo.entities.Todo;
import io.github.com.aplaraujo.entities.User;
import io.github.com.aplaraujo.mappers.TodoMapper;
import io.github.com.aplaraujo.repositories.TodoRepository;
import io.github.com.aplaraujo.repositories.UserRepository;
import io.github.com.aplaraujo.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository repository;
    private final UserRepository userRepository;
    private final TodoMapper mapper;

    public TodoDTO save(TodoDTO dto) {
        Todo todo = mapper.toEntity(dto);
        User user = userRepository.getReferenceById(dto.userId());
        todo.setUser(user);
        todo = repository.save(todo);
        return mapper.toDTO(todo);
    }

    public List<Todo> getAllTodosByUser(Long userId) {
        if (userId != null) {
            return repository.findByUserId(userId);
        }
        return repository.findAll();
    }

    public Optional<Todo> getTodoByIdAndUser(Long id, Long userId) {
        if (id == null || userId == null) {
            throw new ResourceNotFoundException("Tarefa não encontrada ou não pertence ao usuário");
        }
        return repository.findByIdAndUserId(id, userId);
    }

//    public Optional<Todo> findById(Long id) {
//        return repository.findById(id);
//    }

//    public List<TodoDTO> todos() {
//        return repository.findAll().stream().map(todo -> new TodoDTO(todo.getId(), todo.getName(), todo.getDescription(), todo.getDone(), todo.getPriority(), todo.getUser().getId())).toList();
//    }

    public void update(Todo todo) {
        if (todo.getId() == null) {
            throw new IllegalArgumentException("Todo not found!");
        }
        repository.save(todo);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Todo not found with id " + id);
        }
        repository.deleteById(id);
    }
}
