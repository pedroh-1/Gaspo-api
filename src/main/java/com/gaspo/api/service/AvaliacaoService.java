package com.gaspo.api.service;

import com.gaspo.api.dto.request.AvaliacaoRequestDTO;
import com.gaspo.api.dto.response.AvaliacaoResponseDTO;
import com.gaspo.api.model.esus.PacienteModel;
import com.gaspo.api.model.esus.ProfissionalModel;
import com.gaspo.api.model.gaspo.AvaliacaoModel;
import com.gaspo.api.repository.esus.PacienteRepository;
import com.gaspo.api.repository.esus.ProfissionalRepository;
import com.gaspo.api.repository.gaspo.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository repository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

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
        PacienteModel paciente = avaliacao.getPacienteId() != null
                ? pacienteRepository.findById(avaliacao.getPacienteId()).orElse(null)
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
        pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
    }
}
