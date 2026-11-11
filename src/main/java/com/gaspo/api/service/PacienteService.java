package com.gaspo.api.service;

import com.gaspo.api.dto.request.UsuarioCadastroDTO;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.UsuarioRepository;
import com.gaspo.api.util.CpfUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PacienteService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioModel> listarTodos() {
        return usuarioRepository.findAll();
    }

    public List<UsuarioModel> listarUsuariosGaspo() {
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioModel> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public UsuarioModel cadastrarUsuario(UsuarioCadastroDTO dto) {
        String cpf = CpfUtils.normalizar(dto.cpf());
        if (!CpfUtils.valido(cpf)) {
            throw new RuntimeException("CPF inválido.");
        }
        if (usuarioRepository.existsByCpf(cpf) || usuarioRepository.existsByCpf(CpfUtils.formatar(cpf))) {
            throw new RuntimeException("CPF já está cadastrado.");
        }
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("E-mail já está em uso.");
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome(dto.nome());
        usuario.setCpf(cpf);
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        return usuarioRepository.save(usuario);
    }
}
