package io.github.com.aplaraujo.repositories;

import io.github.com.aplaraujo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
