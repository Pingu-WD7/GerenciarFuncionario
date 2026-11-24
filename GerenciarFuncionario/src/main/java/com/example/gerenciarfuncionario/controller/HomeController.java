package com.example.gerenciarfuncionario.controller;

import com.example.gerenciarfuncionario.service.*;
import com.example.gerenciarfuncionario.model.enumStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final FuncionarioService funcionarios;
    private final TarefaService tarefas;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalFuncionarios", funcionarios.contar());
        model.addAttribute("totalTarefas", tarefas.contar());
        model.addAttribute("pendentes", tarefas.contarPorStatus(enumStatus.Pendente));
        model.addAttribute("andamento", tarefas.contarPorStatus(enumStatus.Em_Andamento));
        model.addAttribute("concluidas", tarefas.contarPorStatus(enumStatus.Comcluida));
        return "Home";
    }
}
