package io.github.com.aplaraujo.entities;

import io.github.com.aplaraujo.entities.enums.PriorityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_todo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private Boolean done;

    @Enumerated(EnumType.STRING)
    private PriorityType priority;

    public Todo(String name, String description, Boolean done, PriorityType priority, User user) {
        this.name = name;
        this.description = description;
        this.done = done;
        this.priority = priority;
        this.user = user;
    }

    // Relacionamento muitos-para-um (muitas tarefas para um único usuário)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
