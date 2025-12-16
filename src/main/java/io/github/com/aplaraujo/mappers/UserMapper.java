package io.github.com.aplaraujo.mappers;

import io.github.com.aplaraujo.dto.UserDTO;
import io.github.com.aplaraujo.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }
}
