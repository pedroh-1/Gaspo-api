package com.gaspo.api.service;

import com.gaspo.api.dto.request.AvaliacaoConsultaRequestDTO;
import com.gaspo.api.dto.response.AvaliacaoConsultaResponseDTO;
import com.gaspo.api.model.gaspo.AvaliacaoConsultaModel;
import com.gaspo.api.model.gaspo.ProfissionalModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.AvaliacaoConsultaRepository;
import com.gaspo.api.repository.ProfissionalRepository;
import com.gaspo.api.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AvaliacaoConsultaService {

    private final AvaliacaoConsultaRepository repository;
    private final ProfissionalRepository profissionalRepository;
    private final UsuarioRepository usuarioRepository;

    public AvaliacaoConsultaService(AvaliacaoConsultaRepository repository,
                                    ProfissionalRepository profissionalRepository,
                                    UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.profissionalRepository = profissionalRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public AvaliacaoConsultaResponseDTO avaliar(AvaliacaoConsultaRequestDTO dto, UsuarioModel paciente) {
        if (paciente == null || paciente.getId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Faça login como paciente para avaliar a consulta");
        }
        if (dto.nota() < 1 || dto.nota() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A nota deve ser de 1 a 5");
        }
        if (dto.dataConsulta() == null || dto.dataConsulta().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data da consulta não pode ser no futuro");
        }
        ProfissionalModel profissional = profissionalRepository.findById(dto.profissionalId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado"));

        AvaliacaoConsultaModel avaliacao = new AvaliacaoConsultaModel();
        avaliacao.setNota(dto.nota());
        avaliacao.setComentario(textoOuNulo(dto.comentario()));
        avaliacao.setEspecialidade(textoOuNulo(dto.especialidade()) != null
                ? dto.especialidade().trim()
                : profissional.getEspecialidade());
        avaliacao.setProfissionalId(profissional.getId());
        avaliacao.setDataConsulta(dto.dataConsulta());
        avaliacao.setHorarioConsulta(dto.horarioConsulta());
        avaliacao.setUnidade(textoOuNulo(dto.unidade()));
        avaliacao.setPacienteId(paciente.getId());
        avaliacao.setDataAvaliacao(LocalDateTime.now());

        return toResponse(repository.save(avaliacao), profissional, paciente);
    }

    public List<AvaliacaoConsultaResponseDTO> listarTodas() {
        return repository.findAllByOrderByDataAvaliacaoDesc().stream().map(this::toResponse).toList();
    }

    public List<AvaliacaoConsultaResponseDTO> listarDoPaciente(UsuarioModel paciente) {
        if (paciente == null || paciente.getId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Faça login como paciente");
        }
        return repository.findByPacienteIdOrderByDataAvaliacaoDesc(paciente.getId()).stream()
                .map(this::toResponse)
                .toList();
    }

    private AvaliacaoConsultaResponseDTO toResponse(AvaliacaoConsultaModel avaliacao) {
        ProfissionalModel profissional = profissionalRepository.findById(avaliacao.getProfissionalId()).orElse(null);
        UsuarioModel paciente = usuarioRepository.findById(avaliacao.getPacienteId()).orElse(null);
        return toResponse(avaliacao, profissional, paciente);
    }

    private AvaliacaoConsultaResponseDTO toResponse(AvaliacaoConsultaModel a, ProfissionalModel profissional, UsuarioModel paciente) {
        return new AvaliacaoConsultaResponseDTO(
                a.getId(),
                a.getNota(),
                a.getComentario(),
                a.getEspecialidade(),
                a.getProfissionalId(),
                profissional != null ? profissional.getNome() : null,
                a.getDataConsulta(),
                a.getHorarioConsulta(),
                a.getUnidade(),
                a.getPacienteId(),
                paciente != null ? paciente.getNome() : null,
                a.getDataAvaliacao()
        );
    }

    private static String textoOuNulo(String texto) {
        return (texto == null || texto.isBlank()) ? null : texto.trim();
    }
}
