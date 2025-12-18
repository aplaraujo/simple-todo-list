package io.github.com.aplaraujo.services;

import io.github.com.aplaraujo.dto.TodoDTO;
import io.github.com.aplaraujo.entities.Todo;
import io.github.com.aplaraujo.entities.User;
import io.github.com.aplaraujo.mappers.TodoMapper;
import io.github.com.aplaraujo.repositories.TodoRepository;
import io.github.com.aplaraujo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Optional<Todo> findById(Long id) {
        return repository.findById(id);
    }
}
