package com.example.gerenciarfuncionario.dto;
public record FuncionarioResponseDto(

        Long id,
        String nomeFuncionario,
        String cargoFuncionario,
        String emailFuncionairo
) {
}
