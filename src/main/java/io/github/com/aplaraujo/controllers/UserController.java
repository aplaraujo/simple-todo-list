package io.github.com.aplaraujo.controllers;

import io.github.com.aplaraujo.dto.UserDTO;
import io.github.com.aplaraujo.entities.User;
import io.github.com.aplaraujo.mappers.UserMapper;
import io.github.com.aplaraujo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController implements GenericController{
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody UserDTO dto) {
        User user = userMapper.toEntity(dto);
        userService.save(user);
        var url = generateHeaderLocation(user.getId());
        return ResponseEntity.created(url).build();
    }
}
