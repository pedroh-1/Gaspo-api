package com.gaspo.api.service;

import com.gaspo.api.dto.request.CadastroPacienteDTO;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.UsuarioRepository;
import com.gaspo.api.util.CpfUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CadastroService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public UsuarioModel cadastrarPacienteCompleto(CadastroPacienteDTO dto) {
        String cpf = CpfUtils.normalizar(dto.getCpf());
        if (!CpfUtils.valido(cpf)) {
            throw new RuntimeException("CPF inválido.");
        }
        if (usuarioRepository.existsByCpf(cpf) || usuarioRepository.existsByCpf(CpfUtils.formatar(cpf))) {
            throw new RuntimeException("CPF já está cadastrado.");
        }
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("E-mail já está em uso.");
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome(dto.getNome());
        usuario.setCpf(cpf);
        usuario.setCns(dto.getCns());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        return usuarioRepository.save(usuario);
    }
}
