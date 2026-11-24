package com.example.gerenciarfuncionario.repository;

import com.example.gerenciarfuncionario.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findByNomeFuncionarioContainingIgnoreCaseOrCargoFuncionarioContainingIgnoreCaseOrEmailFuncionairoContainingIgnoreCase(String nome, String cargo, String email);

    boolean existsByEmailFuncionairoIgnoreCase(String email);

    boolean existsByEmailFuncionairoIgnoreCaseAndIdNot(String email, Long id);
}
