package com.example.gerenciarfuncionario.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice(assignableTypes = {FuncionarioController.class, TarefaController.class})
public class ErroController {

    @ExceptionHandler({IllegalArgumentException.class,
            MethodArgumentNotValidException.class,
            DataIntegrityViolationException.class})
    public String tratarErro(Exception erro, HttpServletRequest request,
                             RedirectAttributes atributos) {
        String mensagem = "Confira os dados informados.";

        if (erro instanceof IllegalArgumentException) {
            mensagem = erro.getMessage();
        } else if (erro instanceof DataIntegrityViolationException) {
            mensagem = "Operação não permitida pelo banco de dados.";
        }

        atributos.addFlashAttribute("erro", mensagem);

        if (request.getRequestURI().startsWith(request.getContextPath() + "/tarefa")) {
            return "redirect:/tarefa";
        }
        return "redirect:/funcionario";
    }
}
