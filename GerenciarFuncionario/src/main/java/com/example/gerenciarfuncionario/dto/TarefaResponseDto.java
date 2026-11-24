package com.example.gerenciarfuncionario.dto;

import com.example.gerenciarfuncionario.model.enumStatus;
import java.time.LocalDate;
public record TarefaResponseDto(

        Long id,
        String titulo,
        String descricao,
        LocalDate dataCriacao,
        enumStatus status,
        Long funcionarioId,
        String nomeFuncionario
) {
}
