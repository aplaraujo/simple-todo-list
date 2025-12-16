package io.github.com.aplaraujo.repositories;

import io.github.com.aplaraujo.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
