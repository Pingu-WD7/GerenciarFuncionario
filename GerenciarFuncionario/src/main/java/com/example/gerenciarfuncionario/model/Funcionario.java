package com.example.gerenciarfuncionario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeFuncionario;

    private String cargoFuncionario;

    @Column(unique = true, nullable = false)
    private String emailFuncionairo;

    @OneToMany(mappedBy = "funcionario")
    private List<Tarefa> tarefaList;
}
