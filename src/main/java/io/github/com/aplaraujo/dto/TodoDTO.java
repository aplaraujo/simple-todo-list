package io.github.com.aplaraujo.dto;

import io.github.com.aplaraujo.entities.PriorityType;

public record TodoDTO(
        Long id,
        String name,
        String description,
        Boolean done,
        PriorityType priority,
        UserDTO user
) {
}
