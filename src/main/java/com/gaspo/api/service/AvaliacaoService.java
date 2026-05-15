package com.gaspo.api.service;

import com.gaspo.api.dto.request.AvaliacaoRequestDTO;
import com.gaspo.api.dto.response.AvaliacaoResponseDTO;
import com.gaspo.api.model.gaspo.AvaliacaoModel;
import com.gaspo.api.model.gaspo.ProfissionalModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.gaspo.AvaliacaoRepository;
import com.gaspo.api.repository.gaspo.ProfissionalRepository;
import com.gaspo.api.repository.gaspo.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ProfissionalRepository profissionalRepository;

    public AvaliacaoService(AvaliacaoRepository repository,
                            UsuarioRepository usuarioRepository,
                            ProfissionalRepository profissionalRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.profissionalRepository = profissionalRepository;
    }

    public AvaliacaoModel salvar(AvaliacaoModel avaliacao) {
        validarPacienteEProfissional(avaliacao.getPacienteId(), avaliacao.getProfissionalId());
        if (avaliacao.getDataAvaliacao() == null) {
            avaliacao.setDataAvaliacao(new Date());
        }
        return repository.save(avaliacao);
    }

    public AvaliacaoResponseDTO avaliar(AvaliacaoRequestDTO request) {
        AvaliacaoModel avaliacao = new AvaliacaoModel(
                request.nota(),
                request.comentario(),
                request.pacienteId(),
                request.profissionalId()
        );

        return toResponse(salvar(avaliacao));
    }

    public List<AvaliacaoResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<AvaliacaoResponseDTO> listarPorProfissional(Long profissionalId) {
        profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        return repository.findByProfissionalIdOrderByDataAvaliacaoDesc(profissionalId).stream()
                .map(this::toResponse)
                .toList();
    }

    public AvaliacaoResponseDTO toResponse(AvaliacaoModel avaliacao) {
        UsuarioModel paciente = avaliacao.getPacienteId() != null
                ? usuarioRepository.findById(avaliacao.getPacienteId()).orElse(null)
                : null;
        ProfissionalModel profissional = avaliacao.getProfissionalId() != null
                ? profissionalRepository.findById(avaliacao.getProfissionalId()).orElse(null)
                : null;

        return new AvaliacaoResponseDTO(
                avaliacao.getId(),
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getPacienteId(),
                paciente != null ? paciente.getNome() : "Paciente não encontrado",
                avaliacao.getProfissionalId(),
                profissional != null ? profissional.getNome() : "Profissional não encontrado",
                profissional != null ? profissional.getEspecialidade() : null,
                avaliacao.getDataAvaliacao()
        );
    }

    private void validarPacienteEProfissional(Long pacienteId, Long profissionalId) {
        if (pacienteId == null) {
            throw new RuntimeException("Paciente é obrigatório");
        }
        if (profissionalId == null) {
            throw new RuntimeException("Profissional é obrigatório");
        }
        usuarioRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
    }
}
