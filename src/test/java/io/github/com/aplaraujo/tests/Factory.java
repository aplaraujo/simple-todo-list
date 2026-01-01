package io.github.com.aplaraujo.tests;

import io.github.com.aplaraujo.entities.Todo;
import io.github.com.aplaraujo.entities.User;
import io.github.com.aplaraujo.entities.enums.PriorityType;

public class Factory {
    public static User createUser() {
        return new User(3L, "Mariazinha", "mariazinha@gmail.com", "123456");
    }

    public static Todo createTodo() {
        return new Todo(1L, "Estudar Java", "Lorem ipsum dolor sit amet", true, PriorityType.ALTA, createUser());
    }

}
