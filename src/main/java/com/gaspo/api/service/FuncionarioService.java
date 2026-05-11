package com.gaspo.api.service;

import com.gaspo.api.dto.request.FuncionarioCadastroDTO;
import com.gaspo.api.dto.response.FuncionarioResponseDTO;
import com.gaspo.api.model.gaspo.FuncionarioModel;
import com.gaspo.api.repository.gaspo.FuncionarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<FuncionarioResponseDTO> listarTodos() {
        return funcionarioRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(transactionManager = "gaspoTransactionManager")
    public FuncionarioResponseDTO cadastrar(FuncionarioCadastroDTO dto) {
        if (funcionarioRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("E-mail já está em uso por outro funcionário.");
        }

        FuncionarioModel funcionario = new FuncionarioModel();
        funcionario.setNome(dto.nome());
        funcionario.setEmail(dto.email());
        funcionario.setTelefone(dto.telefone());
        funcionario.setCargo(dto.cargo());
        funcionario.setSenha(passwordEncoder.encode(dto.senha()));

        return toResponse(funcionarioRepository.save(funcionario));
    }

    private FuncionarioResponseDTO toResponse(FuncionarioModel funcionario) {
        return new FuncionarioResponseDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getEmail(),
                funcionario.getTelefone(),
                funcionario.getCargo()
        );
    }
}
