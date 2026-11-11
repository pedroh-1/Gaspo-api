package com.gaspo.api.controller;

import com.gaspo.api.dto.request.AvaliacaoConsultaRequestDTO;
import com.gaspo.api.dto.response.AvaliacaoConsultaResponseDTO;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.service.AvaliacaoConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Avaliação do atendimento da consulta, feita pelo paciente logado. */
@RestController
@RequestMapping("/api/avaliacoes-consulta")
public class AvaliacaoConsultaController {

    private final AvaliacaoConsultaService service;

    public AvaliacaoConsultaController(AvaliacaoConsultaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AvaliacaoConsultaResponseDTO> avaliar(@Valid @RequestBody AvaliacaoConsultaRequestDTO request,
                                                                @AuthenticationPrincipal UsuarioModel pacienteLogado) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.avaliar(request, pacienteLogado));
    }

    /** Avaliações do paciente logado. */
    @GetMapping("/minhas")
    public ResponseEntity<List<AvaliacaoConsultaResponseDTO>> minhas(@AuthenticationPrincipal UsuarioModel pacienteLogado) {
        return ResponseEntity.ok(service.listarDoPaciente(pacienteLogado));
    }

    /** Todas as avaliações (funcionário). */
    @GetMapping
    public ResponseEntity<List<AvaliacaoConsultaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }
}
