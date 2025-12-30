package io.github.com.aplaraujo.entities;

// Começar pelas entidades independentes
// Os nomes da classe e seus atributos devem sempre respeitar o diagrama
// Mapeamento das entidades para visualização no banco de dados H2

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    // Relacionamento um-para-muitos (um único usuário tem uma ou mais tarefas)
    @OneToMany(mappedBy = "user") // Nome do atributo criado na outra classe
    @JsonIgnore
    private List<Todo> todos = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tb_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Transient
    private List<String> authority;
}
