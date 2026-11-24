package com.example.gerenciarfuncionario.dto;

import jakarta.validation.constraints.*;
public record TarefaRequestDto(
        @NotBlank @Size(max = 255)
        String titulo,
        @NotBlank @Size(max = 255)
        String descricao,
        @NotNull
        Long funcionarioId
) {
}
