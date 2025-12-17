package io.github.com.aplaraujo.services;

import io.github.com.aplaraujo.entities.User;
import io.github.com.aplaraujo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
}
