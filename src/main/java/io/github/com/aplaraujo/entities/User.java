package io.github.com.aplaraujo.entities;

// Começar pelas entidades independentes
// Os nomes da classe e seus atributos devem sempre respeitar o diagrama
// Mapeamento das entidades para visualização no banco de dados H2

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String login;
    private String password;
    private String nome;
}
