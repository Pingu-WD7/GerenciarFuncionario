package com.example.gerenciarfuncionario.service;

import com.example.gerenciarfuncionario.dto.*;
import com.example.gerenciarfuncionario.model.*;
import com.example.gerenciarfuncionario.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final FuncionarioService funcionarioService;

    private Tarefa obter(Long id) {
        return tarefaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));
    }

    private TarefaResponseDto converterParaResponse(Tarefa t) {
        return new TarefaResponseDto(
                t.getId(),
                t.getTitulo(),
                t.getDescricao(),
                t.getDataCriacao(),
                t.getStatus(),
                t.getFuncionario().getId(),
                t.getFuncionario().getNomeFuncionario()
        );
    }

    private void preencher(Tarefa t, TarefaRequestDto dto) {
        t.setTitulo(dto.titulo().trim());
        t.setDescricao(dto.descricao().trim());
        t.setFuncionario(funcionarioService.obter(dto.funcionarioId()));
    }

    @Transactional(readOnly = true)
    public List<TarefaResponseDto> pesquisar(String q, enumStatus status, Long funcionarioId) {
        List<Tarefa> tarefas;

        if (q.isBlank()) {
            tarefas = tarefaRepository.findAll();
        } else {
            tarefas = tarefaRepository
                    .findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(q, q);
        }

        // Filtros opcionais: combina texto, status e responsável.
        List<TarefaResponseDto> resultado = new ArrayList<>();

        for (Tarefa tarefa : tarefas) {
            if (status != null && tarefa.getStatus() != status) {
                continue;
            }
            if (funcionarioId != null && !funcionarioId.equals(tarefa.getFuncionario().getId())) {
                continue;
            }
            resultado.add(converterParaResponse(tarefa));
        }

        return resultado;
    }

    @Transactional(readOnly = true)
    public TarefaResponseDto buscarPorId(Long id) {
        return converterParaResponse(obter(id));
    }

    public TarefaResponseDto salvar(TarefaRequestDto dto) {
        Tarefa t = new Tarefa();
        preencher(t, dto);
        t.setDataCriacao(LocalDate.now());
        t.setStatus(enumStatus.Pendente);
        return converterParaResponse(tarefaRepository.save(t));
    }

    public TarefaResponseDto atualizar(Long id, TarefaRequestDto dto) {
        Tarefa t = obter(id);
        preencher(t, dto);
        return converterParaResponse(tarefaRepository.save(t));
    }

    public void alterarStatus(Long id, enumStatus status) {
        Tarefa tarefa = obter(id);
        tarefa.setStatus(status);
        tarefaRepository.save(tarefa);
    }

    public void excluir(Long id) {
        tarefaRepository.delete(obter(id));
    }

    public long contar() {
        return tarefaRepository.count();
    }

    public long contarPorStatus(enumStatus status) {
        return tarefaRepository.countByStatus(status);
    }
}
