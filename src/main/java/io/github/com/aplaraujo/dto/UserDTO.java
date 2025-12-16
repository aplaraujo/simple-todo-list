package io.github.com.aplaraujo.dto;

import io.github.com.aplaraujo.entities.Todo;

import java.util.List;

public record UserDTO(
        Long id,
        String name,
        String email,
        String password,
        List<Todo> todos
) {
}
