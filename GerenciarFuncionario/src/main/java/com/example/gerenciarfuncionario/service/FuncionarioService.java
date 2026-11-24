package com.example.gerenciarfuncionario.service;

import com.example.gerenciarfuncionario.dto.*;
import com.example.gerenciarfuncionario.model.Funcionario;
import com.example.gerenciarfuncionario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final TarefaRepository tarefaRepository;

    private FuncionarioResponseDto converterParaResponse(Funcionario f) {
        return new FuncionarioResponseDto(
                f.getId(),
                f.getNomeFuncionario(),
                f.getCargoFuncionario(),
                f.getEmailFuncionairo()
        );
    }

    public Funcionario obter(Long id) {
        return funcionarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<FuncionarioResponseDto> listar() {
        return pesquisar("");
    }

    @Transactional(readOnly = true)
    public List<FuncionarioResponseDto> pesquisar(String q) {
        if (q.isBlank()) {
            return funcionarioRepository.findAll()
                    .stream()
                    .map(this::converterParaResponse)
                    .toList();
        }

        return funcionarioRepository
                .findByNomeFuncionarioContainingIgnoreCaseOrCargoFuncionarioContainingIgnoreCaseOrEmailFuncionairoContainingIgnoreCase(q, q, q)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public FuncionarioResponseDto buscarPorID(Long id) {
        return converterParaResponse(obter(id));
    }

    private void preencher(Funcionario f, FuncionarioRequestDto dto) {
        f.setNomeFuncionario(dto.nomeFuncionario().trim());
        f.setCargoFuncionario(dto.cargoFuncionario().trim());
        f.setEmailFuncionairo(dto.emailFuncionairo().trim().toLowerCase(java.util.Locale.ROOT));
    }

    public FuncionarioResponseDto salvar(FuncionarioRequestDto dto) {
        if (funcionarioRepository.existsByEmailFuncionairoIgnoreCase(dto.emailFuncionairo().trim())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
        Funcionario f = new Funcionario();
        preencher(f, dto);
        return converterParaResponse(funcionarioRepository.save(f));
    }

    public FuncionarioResponseDto atualizarFuncionario(Long id, FuncionarioRequestDto dto) {
        Funcionario f = obter(id);
        if (funcionarioRepository.existsByEmailFuncionairoIgnoreCaseAndIdNot(dto.emailFuncionairo().trim(), id)) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
        preencher(f, dto);
        return converterParaResponse(funcionarioRepository.save(f));
    }

    public void deletarFuncionario(Long id) {
        Funcionario f = obter(id);
        if (tarefaRepository.existsByFuncionario_Id(id)) {
            throw new IllegalArgumentException("Exclua ou transfira as tarefas antes de excluir o funcionário");
        }
        funcionarioRepository.delete(f);
    }

    public long contar() {
        return funcionarioRepository.count();
    }
}
