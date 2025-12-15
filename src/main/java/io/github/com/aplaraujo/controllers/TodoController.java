package io.github.com.aplaraujo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/todos")
public class TodoController {

    @GetMapping
    public String teste() {
        return "Rota de tarefas";
    }
}
