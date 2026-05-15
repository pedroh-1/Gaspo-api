package com.gaspo.api.service;

import com.gaspo.api.dto.request.UsuarioCadastroDTO;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.gaspo.UsuarioRepository;
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
        if (usuarioRepository.existsByCpf(dto.cpf())) {
            throw new RuntimeException("CPF já está cadastrado.");
        }
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("E-mail já está em uso.");
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        return usuarioRepository.save(usuario);
    }
}
