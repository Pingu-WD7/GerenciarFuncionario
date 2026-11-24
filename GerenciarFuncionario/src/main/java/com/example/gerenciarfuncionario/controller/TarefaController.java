package com.example.gerenciarfuncionario.controller;

import com.example.gerenciarfuncionario.dto.*;
import com.example.gerenciarfuncionario.model.enumStatus;
import com.example.gerenciarfuncionario.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tarefa")
@RequiredArgsConstructor
public class TarefaController {
    private final TarefaService tarefaService;
    private final FuncionarioService funcionarioService;

    @GetMapping
    public String listar(@RequestParam(defaultValue = "") String q, @RequestParam(required = false) enumStatus status, @RequestParam(required = false) Long funcionarioId, Model model) {
        model.addAttribute("tarefas", tarefaService.pesquisar(q, status, funcionarioId));
        model.addAttribute("funcionarios", funcionarioService.listar());
        model.addAttribute("statusList", enumStatus.values());
        model.addAttribute("q", q);
        model.addAttribute("status", status);
        model.addAttribute("funcionarioId", funcionarioId);
        return "Tarefa";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute TarefaRequestDto dto, RedirectAttributes ra) {
        tarefaService.salvar(dto);
        ra.addFlashAttribute("sucesso", "Tarefa criada!");
        return "redirect:/tarefa";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("tarefa", tarefaService.buscarPorId(id));
        model.addAttribute("funcionarios", funcionarioService.listar());
        return "EditarTarefa";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute TarefaRequestDto dto, RedirectAttributes ra) {
        tarefaService.atualizar(id, dto);
        ra.addFlashAttribute("sucesso", "Tarefa atualizada!");
        return "redirect:/tarefa";
    }

    @PostMapping("/status/{id}")
    public String status(@PathVariable Long id, @RequestParam enumStatus status, RedirectAttributes ra) {
        tarefaService.alterarStatus(id, status);
        ra.addFlashAttribute("sucesso", "Status atualizado!");
        return "redirect:/tarefa";
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes ra) {
        tarefaService.excluir(id);
        ra.addFlashAttribute("sucesso", "Tarefa excluída!");
        return "redirect:/tarefa";
    }
}
