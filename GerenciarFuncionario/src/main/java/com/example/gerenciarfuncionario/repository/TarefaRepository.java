package com.example.gerenciarfuncionario.repository;

import com.example.gerenciarfuncionario.model.Tarefa;
import com.example.gerenciarfuncionario.model.enumStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(
            String titulo, String descricao);

    List<Tarefa> findByStatus(enumStatus status);

    List<Tarefa> findByFuncionario_Id(Long id);

    boolean existsByFuncionario_Id(Long id);

    long countByStatus(enumStatus status);
}
