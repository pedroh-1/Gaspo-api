package com.gaspo.api.service;

import com.gaspo.api.dto.request.FuncionarioCadastroDTO;
import com.gaspo.api.dto.response.FuncionarioResponseDTO;
import com.gaspo.api.mapper.FuncionarioMapper;
import com.gaspo.api.model.gaspo.FuncionarioModel;
import com.gaspo.api.repository.FuncionarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioService(FuncionarioRepository funcionarioRepository,
                              PasswordEncoder passwordEncoder,
                              FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.funcionarioMapper = funcionarioMapper;
    }

    public List<FuncionarioResponseDTO> listarTodos() {
        return funcionarioMapper.toResponseDTOList(funcionarioRepository.findAll());
    }

    @Transactional(transactionManager = "gaspoTransactionManager")
    public FuncionarioResponseDTO cadastrar(FuncionarioCadastroDTO dto) {
        if (funcionarioRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("E-mail já está em uso por outro funcionário.");
        }

        FuncionarioModel funcionario = funcionarioMapper.toModel(dto);
        funcionario.setSenha(passwordEncoder.encode(dto.senha()));

        return funcionarioMapper.toResponseDTO(funcionarioRepository.save(funcionario));
    }
}
