package com.gaspo.api.service;

import com.gaspo.api.dto.request.UsuarioCadastroDTO;
import com.gaspo.api.model.esus.PacienteModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.esus.PacienteRepository;
import com.gaspo.api.repository.gaspo.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository, UsuarioRepository usuarioRepository) {
        this.pacienteRepository = pacienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Retorna todos os pacientes (cidadãos) registados no e-SUS.
     */
    public List<PacienteModel> listarTodos() {
        return pacienteRepository.findAll();
    }

    /**
     * Retorna todos os usuários registrados no banco local (Gaspo).
     */
    public List<UsuarioModel> listarUsuariosGaspo() {
        return usuarioRepository.findAll();
    }

    /**
     * Procura um paciente específico pelo ID interno do sistema.
     */
    public Optional<PacienteModel> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    @Transactional
    public UsuarioModel cadastrarUsuario(UsuarioCadastroDTO dto) {
        PacienteModel pacienteEsus = pacienteRepository.findFirstByCpf(dto.cpf())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cidadão não encontrado na base do e-SUS. Por favor, compareça à UBS (Unidade Básica de Saúde) mais próxima para realizar seu cadastro."
                ));

        if (usuarioRepository.existsByIdCidadaoEsus(pacienteEsus.getId())) {
            throw new RuntimeException("Cidadão já possui cadastro no aplicativo.");
        }

        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("E-mail já está em uso.");
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setEmail(dto.email());
        usuario.setNome(pacienteEsus.getNome());
        usuario.setCpf(pacienteEsus.getCpf());
        // TODO: Criptografar a senha (ex: passwordEncoder.encode(dto.senha()))
        usuario.setSenha(dto.senha());
        usuario.setIdCidadaoEsus(pacienteEsus.getId());

        return usuarioRepository.save(usuario);
    }
}