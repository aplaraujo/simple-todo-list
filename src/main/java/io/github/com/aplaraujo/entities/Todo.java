package io.github.com.aplaraujo.entities;

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

    // Relacionamento muitos-para-um (muitas tarefas para um único usuário)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
