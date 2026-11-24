package com.example.gerenciarfuncionario.controller;

import com.example.gerenciarfuncionario.dto.*;
import com.example.gerenciarfuncionario.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/funcionario")
@RequiredArgsConstructor
public class FuncionarioController {
    private final FuncionarioService funcionarioService;

    @GetMapping
    public String listar(@RequestParam(defaultValue = "") String q, Model model) {
        model.addAttribute("funcionarios", funcionarioService.pesquisar(q));
        model.addAttribute("q", q);
        return "Funcionario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute FuncionarioRequestDto dto, RedirectAttributes ra) {
        funcionarioService.salvar(dto);
        ra.addFlashAttribute("sucesso", "Funcionário cadastrado!");
        return "redirect:/funcionario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("funcionario", funcionarioService.buscarPorID(id));
        return "EditarFuncionario";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute FuncionarioRequestDto dto, RedirectAttributes ra) {
        funcionarioService.atualizarFuncionario(id, dto);
        ra.addFlashAttribute("sucesso", "Funcionário atualizado!");
        return "redirect:/funcionario";
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes ra) {
        funcionarioService.deletarFuncionario(id);
        ra.addFlashAttribute("sucesso", "Funcionário excluído!");
        return "redirect:/funcionario";
    }
}
