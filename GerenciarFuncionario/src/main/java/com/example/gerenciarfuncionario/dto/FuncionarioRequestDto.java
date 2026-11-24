package com.example.gerenciarfuncionario.dto;

import jakarta.validation.constraints.*;

public record FuncionarioRequestDto(
        @NotBlank @Size(max = 255)
        String nomeFuncionario,
        @NotBlank @Size(max = 255)
        String cargoFuncionario,
        @NotBlank @Email @Size(max = 255)
        String emailFuncionairo
) {
}
